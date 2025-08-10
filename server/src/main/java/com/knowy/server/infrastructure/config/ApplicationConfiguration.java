package com.knowy.server.infrastructure.config;

import com.knowy.server.application.*;
import com.knowy.server.application.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public KnowyPasswordEncoder knowyPasswordEncoder(PasswordEncoder passwordEncoder) {
		return passwordEncoder::encode;
	}

	@Bean
	public UserPrivateService privateUserService(
		UserPrivateRepository privateUserRepository,
		KnowyPasswordChecker knowyPasswordChecker,
		KnowyPasswordEncoder knowyPasswordEncoder,
		KnowyTokenTools knowyTokenTools
	) {
		return new UserPrivateService(
			privateUserRepository, knowyPasswordChecker, knowyPasswordEncoder, knowyTokenTools
		);
	}

	@Bean
	public UserFacadeService userFacadeService(
		KnowyEmailClientTool knowyEmailClientTool,
		UserPrivateService userPrivateService,
		UserService userService
	) {
		return new UserFacadeService(
			knowyEmailClientTool,
			userPrivateService,
			userService
		);
	}

	@Bean
	public UserExerciseService exerciseService(
		UserExerciseRepository userExerciseRepository,
		UserRepository userRepository,
		ExerciseRepository exerciseRepository
	) {
		return new UserExerciseService(userExerciseRepository, userRepository, exerciseRepository);
	}

	@Bean
	public UserLessonService userLessonService(
		UserLessonRepository userLessonRepository,
		LessonRepository lessonRepository
	) {
		return new UserLessonService(userLessonRepository, lessonRepository);
	}

	@Bean
	public UserService userService(
		UserRepository userRepository,
		CategoryRepository categoryRepository,
		ProfileImageRepository profileImageRepository
	) {
		return new UserService(userRepository, categoryRepository, profileImageRepository);
	}

	@Bean
	public CategoryService categoryService(CategoryRepository categoryRepository) {
		return new CategoryService(categoryRepository);
	}

	@Bean
	public CourseService courseService(
		CourseRepository courseRepository,
		LessonRepository lessonRepository,
		UserLessonRepository userLessonRepository,
		CategoryRepository categoryRepository
	) {
		return new CourseService(courseRepository, lessonRepository, userLessonRepository, categoryRepository);
	}


}
