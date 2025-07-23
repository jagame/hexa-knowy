package com.knowy.server.controller;

import com.knowy.server.controller.dto.NewsDto;
import com.knowy.server.service.CourseSubscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PresentationController {

	private final CourseSubscriptionService courseSubscriptionService;

	public PresentationController(CourseSubscriptionService courseSubscriptionService) {
		this.courseSubscriptionService = courseSubscriptionService;
	}

	@GetMapping("/")
	public String viewLandingPage(ModelMap interfaceScreen) {

		List<NewsDto> newsList = courseSubscriptionService.findAllCourses()
			.stream()
			.map(NewsDto::fromEntity)
			.limit(3)
			.toList();

		interfaceScreen.addAttribute("newsList", newsList);
		return "pages/landing-page";
	}
}
