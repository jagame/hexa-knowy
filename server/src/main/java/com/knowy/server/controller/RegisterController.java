package com.knowy.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.knowy.server.model.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
	@GetMapping("/")
	public String home() {
		return "redirect:/register";
	}

	@GetMapping("/register")
	public String Register(Model model) {
		model.addAttribute("user", new User());
		return "knowy-registro";
	}

	@PostMapping("/resultado")
	public String procesarFormulario(@ModelAttribute User user, Model model) {
		System.out.println("Usuario recibido: " + user.getUsername());
		System.out.println("Email recibido: " + user.getEmail());
		System.out.println("Contraseña recibida: " + user.getPassword());
		model.addAttribute("user", user);
		return "resultado";
	}
}
