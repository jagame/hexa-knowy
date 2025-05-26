package com.knowy.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserHomeController {

	@GetMapping("/home")
	public String userHome() {
		return "pages/user-home";
	}

}
