package com.knowy.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserHomeController {

	@GetMapping("/home")
	public String userHome() {
		return "pages/user-home";
	}
}
