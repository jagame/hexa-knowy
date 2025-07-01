package com.knowy.server.controller;

import com.knowy.server.controller.dto.LoginFormDto;
import com.knowy.server.controller.dto.UserDto;
import com.knowy.server.service.AccessService;
import com.knowy.server.service.exception.InvalidUserException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

	@PostMapping("/register")
	public String procesarFormulario(@Valid @ModelAttribute UserDto user, Model model, Errors errors) {
		if (errors.hasErrors()) {
			return "pages/access/register";
		}

		try {
			accessService.registerNewUser(user);
			return "redirect:/home";
		} catch (InvalidUserException e) {
			model.addAttribute("user", user);
			model.addAttribute("error", e.getMessage());
			return "pages/access/register";
		}
	}

	@GetMapping("/login")
	public String viewLogin(Model model) {
		LoginFormDto loginForm = new LoginFormDto();
		model.addAttribute("loginForm", loginForm);
		return "pages/access/login";
	}

	@PostMapping("/login")
	public String postLogin(@ModelAttribute("loginForm") LoginFormDto login, Model model) {
		System.out.println("Email: " + login.getEmail() + " Password: " + login.getPassword() + "");
		model.addAttribute("loginForm", login);
		return "pages/access/login";
	}

//	@GetMapping("/password-change/email")
//	public String passwordChangeEmail(Model model) {
//		model.addAttribute("emailForm", new UserEmailFormDto());
//
//		return "pages/access/password-change-email";
//	}
//
//	@PostMapping("/password-change/email")
//	public String passwordChangeEmail(@ModelAttribute("emailForm") UserEmailFormDto email) {
//		accessService.sendEmailWithToken(email.getEmail());
//
//		return "redirect:/login";
//	}
//
//	@GetMapping("/password-change")
//	public String passwordChange(
//		@RequestParam String token,
//		Model model
//	) {
//		if (accessService.isTokenRegistered(token)) {
//			model.addAttribute("token", token);
//			model.addAttribute("passwordForm", new UserPasswordFormDto());
//			return "pages/access/password-change";
//		}
//		return "redirect:/";
//	}
//
//	@PostMapping("/password-change")
//	public String passwordChange(
//		@RequestParam String token,
//		@ModelAttribute("passwordForm") UserPasswordFormDto userPasswordFormDto
//	) {
//		if (accessService.isTokenRegistered(token)) {
//			// TODO - Borrar
//			System.out.println("Contraseña de Usuario recibida -> 1:" + userPasswordFormDto.getPassword() + "2:" + userPasswordFormDto.getConfirmPassword());
//			accessService.updateUserPassword(
//				token,
//				userPasswordFormDto.getPassword(),
//				userPasswordFormDto.getConfirmPassword()
//			);
//		}
//		return "redirect:/login";
//	}
}
