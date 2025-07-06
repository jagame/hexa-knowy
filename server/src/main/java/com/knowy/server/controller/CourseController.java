package com.knowy.server.controller;

import com.knowy.server.controller.dto.CourseCardDTO;
import com.knowy.server.entity.CourseEntity;
import com.knowy.server.service.CourseSubscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

	private final CourseSubscriptionService courseSubscriptionService;

	public CourseController(CourseSubscriptionService courseSubscriptionService) {
		this.courseSubscriptionService = courseSubscriptionService;
	}

	private CourseCardDTO mapToCardDTO(CourseEntity courseEntity) {
		CourseCardDTO dto = new CourseCardDTO();
		dto.setId(courseEntity.getId());
		dto.setName(courseEntity.getTitle());
		dto.setCreator("Equipo Knowy");
		dto.setProgress(0);
		dto.setAction("Empezar curso");
		dto.setTags(new ArrayList<>(List.of("Java", "SQL", "Spring")));
		return dto;
	}

	private CourseCardDTO mapToRecomendationCardDTO(CourseEntity courseEntity) {
		CourseCardDTO dto = new CourseCardDTO();
		dto.setId(courseEntity.getId());
		dto.setName(courseEntity.getTitle());
		dto.setCreator("Equipo Knowy");
		dto.setProgress(0);
		dto.setAction("Adquirir curso");
		dto.setTags(new ArrayList<>(List.of("Java", "SQL", "Spring")));
		return dto;
	}

	@GetMapping("/my")
	public String myCourses (Model model){
		//TODO
		Integer userId = 1;

		List<CourseEntity> allCourses = courseSubscriptionService.getAllCourses();
		List<CourseEntity> userCourses = courseSubscriptionService.getCoursesForUser(userId);

		List<CourseEntity> recommendedCourses = allCourses.stream()
			.filter(course -> !userCourses.contains(course))
			.toList();

		List<CourseCardDTO>recommendedCoursesCards = recommendedCourses.stream()
			.map(this::mapToRecomendationCardDTO)
			.toList();

		List<CourseCardDTO> courseCards = userCourses.stream()
			.map(this::mapToCardDTO)
			.toList();

		model.addAttribute("courses", courseCards);
		model.addAttribute("recommendations", recommendedCoursesCards);
		return "pages/my-courses";
	}

	@PostMapping("/subscribe")
	public String subscribeToCourse(@RequestParam Integer courseId, RedirectAttributes attrs){
		Integer userId = 1;
		boolean success = courseSubscriptionService.subscribeUserToCourse(userId, courseId);
		if (success) {
			attrs.addFlashAttribute("success", "¡Te has suscrito correctamente!");
		} else {
			attrs.addFlashAttribute("error", "Error al adquirir el curso");
		}
		return "redirect:/courses/my";
	}


}
