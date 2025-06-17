package com.knowy.server.controller;

import com.knowy.server.controller.dto.LoginForm;
import com.knowy.server.controller.dto.UserDto;
import com.knowy.server.controller.dto.UserEmailFormDto;
import com.knowy.server.controller.dto.UserPasswordFormDto;
import com.knowy.server.service.AccessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/password-change/email")
	public String passwordChangeEmail(Model model) {
		model.addAttribute("emailForm", new UserEmailFormDto());

		return "pages/access/password-change-email";
	}

	@PostMapping("/password-change/email")
	public String passwordChangeEmail(@ModelAttribute("emailForm") UserEmailFormDto email) {
		accessService.sendEmailWithToken(email.getEmail());

		return "redirect:/login";
	}

	@GetMapping("/password-change")
	public String passwordChange(@RequestParam String token) {
		if (accessService.isTokenRegistered(token)) {
			return "pages/access/password-change";
		}
		return "redirect:/";
	}

	@PostMapping("/password-change")
	public String passwordChange(@RequestParam String token, @ModelAttribute UserPasswordFormDto userPasswordFormDto) {
		if (accessService.isTokenRegistered(token)) {
			accessService.updateUserPassword(
				token,
				userPasswordFormDto.getPassword(),
				userPasswordFormDto.getConfirmPassword()
			);
		}
		return "redirect:/login";
	}
}
