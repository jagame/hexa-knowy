package com.knowy.server.controller;

import com.knowy.server.controller.dto.CourseCardDTO;
import com.knowy.server.entity.CourseEntity;
import com.knowy.server.service.CourseSubscriptionService;
import com.knowy.server.service.model.UserSecurityDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@Controller
@RequestMapping("/my-courses")
public class CourseController {

	private final CourseSubscriptionService courseSubscriptionService;

	public CourseController(CourseSubscriptionService courseSubscriptionService) {
		this.courseSubscriptionService = courseSubscriptionService;
	}

	@GetMapping("")
	public String myCourses (Model model, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		model.addAttribute("courses", courseSubscriptionService.getUserCourses(userDetails.getPublicUser().getId()));
		model.addAttribute("recommendations", courseSubscriptionService.getRecommendedCourses(userDetails.getPublicUser().getId()));
		return "pages/my-courses";
	}

	@PostMapping("/subscribe")
	public String subscribeToCourse(
		@RequestParam Integer courseId,
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		RedirectAttributes attrs
	){
		if (!courseSubscriptionService.subscribeUserToCourse(userDetails.getPublicUser().getId(), courseId)) {
			attrs.addFlashAttribute("error", "Error al adquirir el curso");
			return "redirect:/my-courses";
		}
		attrs.addFlashAttribute("success", "¡Te has suscrito correctamente!");
		return "redirect:/my-courses";
	}
}
