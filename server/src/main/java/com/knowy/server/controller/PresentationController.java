package com.knowy.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PresentationController {

	@GetMapping("/")
	public String viewLandingPage() {
		return "pages/example";
	}

	@GetMapping("/password-change/email")
	public String passwordChangeEmail() {
		return "pages/password-change-email";
	}

	@GetMapping("/password-change")
	public String passwordChange() {
		return "pages/password-change";
	}
}
