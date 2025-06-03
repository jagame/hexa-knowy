package com.knowy.server.controller;

import com.knowy.server.controller.model.MissionsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
