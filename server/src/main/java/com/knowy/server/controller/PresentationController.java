package com.knowy.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PresentationController {

	@GetMapping("/")
	public String viewLandingPage() {
		return "pages/landing-page";
	}
}
