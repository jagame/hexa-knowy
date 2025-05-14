package com.knowy.server.controller;

import com.knowy.server.controller.model.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserAccessController {
	@GetMapping("/login")
	public String viewLogin (Model model){
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);

		return "pages/login-index";
	}

	@PostMapping("/login")
	public String postLogin(@ModelAttribute("loginForm") LoginForm loginForm) {
		System.out.println("Email: " + loginForm.getEmail() + " Password: " + loginForm.getPassword() + "");
		if (loginForm.getPassword().equals("1234")) {
			return "pages/example";
		}
		return "pages/login-index";
	}
}
