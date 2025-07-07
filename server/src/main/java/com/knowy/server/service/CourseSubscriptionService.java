package com.knowy.server.service;

import com.knowy.server.entity.*;
import com.knowy.server.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class CourseSubscriptionService {
	private final CourseRepository courseRepository;
	private final LessonRepository lessonRepository;
	public final PublicUserLessonRepository publicUserLessonRepository;

	public CourseSubscriptionService(CourseRepository courseRepository, LessonRepository lessonRepository, PublicUserLessonRepository publicUserLessonRepository) {
		this.courseRepository = courseRepository;
		this.lessonRepository = lessonRepository;
		this.publicUserLessonRepository = publicUserLessonRepository;
	}

	public boolean subscribeUserToCourse(Integer userId, Integer courseId) {
		List<LessonEntity> lessons = lessonRepository.findByCourseId(courseId);
		if(lessons.isEmpty())return false;

		boolean alreadySubscribed = lessons.stream()
			.allMatch(lesson->
				publicUserLessonRepository.existsByUserIdAndLessonId(userId, lesson.getId()));
		if(alreadySubscribed) return true;

		for(LessonEntity lesson:lessons){
			PublicUserLessonIdEntity id = new PublicUserLessonIdEntity(userId, lesson.getId());
			if(!publicUserLessonRepository.existsById(id)){
				PublicUserLessonEntity pul = new PublicUserLessonEntity();
				pul.setUserId(userId);
				pul.setLessonId(lesson.getId());
				pul.setStartDate(LocalDate.now());
				pul.setStatus("pending");
				publicUserLessonRepository.save(pul);
			}
		}
		return true;
	}

	public List<CourseEntity> findCoursesByUserId (Integer userId){
		List<Integer> courseIds = publicUserLessonRepository.findCourseIdsByUserId(userId);
		if(courseIds.isEmpty()) return List.of();
		return courseRepository.findByIdIn(courseIds);
	}

	public List<CourseEntity> getAllCourses(){
		return courseRepository.findAll();
	}

	public int getCourseProgress(Integer userId, Integer courseId){
		int totalLessons = lessonRepository.countByCourseId(courseId);
		if(totalLessons ==0) return 0;
		int completedLessons = publicUserLessonRepository.countByUserIdAndCourseIdAndStatus(userId, courseId, "completed");
		return (int)Math.round((completedLessons*100.0/totalLessons));
	}
	 public List<String> findLanguagesForCourse(CourseEntity course){
		return course.getLanguages().stream()
			.map(LanguageEntity::getName)
			.toList();
	 }

}
