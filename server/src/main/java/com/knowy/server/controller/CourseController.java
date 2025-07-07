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

import java.util.List;

@Controller
@RequestMapping("/my-courses")
public class CourseController {

	private final CourseSubscriptionService courseSubscriptionService;

	public CourseController(CourseSubscriptionService courseSubscriptionService) {
		this.courseSubscriptionService = courseSubscriptionService;
	}

	@GetMapping("")
	public String myCourses (Model model){

		Integer userId = 1; //TODO

		List<CourseEntity> allCourses = courseSubscriptionService.getAllCourses();
		List<CourseEntity> userCourses = courseSubscriptionService.findCoursesByUserId(userId);

		List<CourseCardDTO> courseCards = userCourses.stream()
			.map(course-> CourseCardDTO.fromEntity(
				course, courseSubscriptionService.getCourseProgress(userId, course.getId()),
				courseSubscriptionService.findLanguagesForCourse(course))).toList();

		List<CourseCardDTO> recommendedCoursesCards = allCourses.stream()
			.filter(course-> !userCourses.contains(course))
			.map(course-> CourseCardDTO.fromRecommendation(
				course, courseSubscriptionService.findLanguagesForCourse(course))).toList();

		model.addAttribute("courses", courseCards);
		model.addAttribute("recommendations", recommendedCoursesCards);
		return "pages/my-courses";
	}

	@PostMapping("/subscribe")
	public String subscribeToCourse(@RequestParam Integer courseId, RedirectAttributes attrs){
		Integer userId = 1; //TODO -> change fron users from login
		boolean success = courseSubscriptionService.subscribeUserToCourse(userId, courseId);
		if (success) {
			attrs.addFlashAttribute("success", "¡Te has suscrito correctamente!");
		} else {
			attrs.addFlashAttribute("error", "Error al adquirir el curso");
		}
		return "redirect:/my-courses";
	}


}
