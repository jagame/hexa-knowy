package com.knowy.server.controller;

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
		return "pages/knowy-registro";
	}

	@PostMapping("/resultado")
	public String procesarFormulario(@ModelAttribute UserDto user, Model model) {
		System.out.println("Usuario recibido: " + user.getUsername());
		System.out.println("Email recibido: " + user.getEmail());
		System.out.println("Contraseña recibida: " + user.getPassword());
		model.addAttribute("user", user);
		return "pages/resultado";
	}
}
