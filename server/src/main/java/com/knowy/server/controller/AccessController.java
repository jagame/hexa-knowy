package com.knowy.server.controller;

import com.knowy.server.controller.model.LoginForm;
import com.knowy.server.controller.model.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccessController {

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new UserDto());
		return "pages/access/register";
	}

	@PostMapping("/resultado")
	public String procesarFormulario(@ModelAttribute UserDto user, Model model) {
		System.out.println("Usuario recibido: " + user.getUsername());
		System.out.println("Email recibido: " + user.getEmail());
		System.out.println("Contraseña recibida: " + user.getPassword());

		model.addAttribute("user", new UserDto());

		return "pages/access/register";
	}

	@GetMapping("/login")
	public String viewLogin (Model model){
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		return "pages/access/login";
	}

	@PostMapping("/login")
	public String postLogin(@ModelAttribute("loginForm") LoginForm login, Model model) {
		System.out.println("Email: " + login.getEmail() + " Password: " + login.getPassword() + "");
		model.addAttribute("loginForm", login);
		return "pages/access/login";
	}
}
