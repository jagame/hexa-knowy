package com.knowy.server.infrastructure.controller;

import com.knowy.server.application.service.CourseSubscriptionService;
import com.knowy.server.infrastructure.controller.dto.NewsDto;
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
			.map(NewsDto::fromDomain)
			.limit(3)
			.toList();

		interfaceScreen.addAttribute("newsList", newsList);
		return "pages/landing-page";
	}
}
