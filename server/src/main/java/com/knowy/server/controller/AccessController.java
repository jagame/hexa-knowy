package com.knowy.server.controller;

import com.knowy.server.controller.dto.*;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.service.AccessService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AccessController {

	AccessService accessService;

	public AccessController(AccessService accessService) {
		this.accessService = accessService;
	}

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
	public String viewLogin(Model model) {
		LoginFormDto loginForm = new LoginFormDto();
		model.addAttribute("loginForm", loginForm);
		return "pages/access/login";
	}

	@PostMapping("/login")
	public String postLogin(@ModelAttribute("loginForm") LoginFormDto login, Model model, HttpSession session) {
		Optional<AuthResultDto> authResult = accessService.authenticateUser(login.getEmail(), login.getPassword());

		if (authResult.isPresent()) {
			PrivateUserEntity user = authResult.get().getUser();
			String token = authResult.get().getToken();

			session.setAttribute("loggedUser", user);
			session.setAttribute("authToken", token);
			System.out.println("Login correcto. Token generado: " + token);
			return "redirect:/home";
		} else {
			model.addAttribute("loginError", "¡Las credenciales son incorrectas!");
			model.addAttribute("loginForm", new LoginFormDto());
			return "pages/access/login";
		}
	}

	@GetMapping("/password-change/email")
	public String passwordChangeEmail(Model model) {
		model.addAttribute("emailForm", new UserEmailFormDto());

		return "pages/access/password-change-email";
	}

//	@PostMapping("/password-change/email")
//	public String passwordChangeEmail(@ModelAttribute("emailForm") UserEmailFormDto email) {
//		accessService.sendEmailWithToken(email.getEmail());
//
//		return "redirect:/login";
//	}

	@GetMapping("/password-change")
	public String passwordChange(
		@RequestParam String token,
		Model model
	) {
		if (accessService.isTokenRegistered(token)) {
			model.addAttribute("token", token);
			model.addAttribute("passwordForm", new UserPasswordFormDto());
			return "pages/access/password-change";
		}
		return "redirect:/";
	}

	@PostMapping("/password-change")
	public String passwordChange(
		@RequestParam String token,
		@ModelAttribute("passwordForm") UserPasswordFormDto userPasswordFormDto
	) {
		if (accessService.isTokenRegistered(token)) {
			// TODO - Borrar
			System.out.println("Contraseña de Usuario recibida -> 1:" + userPasswordFormDto.getPassword() + "2:" + userPasswordFormDto.getConfirmPassword());
			accessService.updateUserPassword(
				token,
				userPasswordFormDto.getPassword(),
				userPasswordFormDto.getConfirmPassword()
			);
		}
		return "redirect:/login";
	}
}
