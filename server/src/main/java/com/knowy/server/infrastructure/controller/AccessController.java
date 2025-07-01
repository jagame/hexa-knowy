package com.knowy.server.infrastructure.controller;

import com.knowy.server.application.service.PrivateUserService;
import com.knowy.server.application.service.usecase.login.LoginResult;
import com.knowy.server.application.service.usecase.login.LoginCommand;
import com.knowy.server.infrastructure.controller.dto.LoginFormDto;
import com.knowy.server.infrastructure.controller.dto.UserDto;
import com.knowy.server.infrastructure.controller.dto.UserEmailFormDto;
import com.knowy.server.infrastructure.controller.dto.UserPasswordFormDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class AccessController {

	private final PrivateUserService privateUserService;

	public AccessController(PrivateUserService privateUserService) {
		this.privateUserService = privateUserService;
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
		var userLogin = new LoginCommand(login.getEmail(), login.getPassword());

		return privateUserService.doLogin(userLogin)
			.map(loginResult -> loginSuccess(session, loginResult))
			.orElseGet(() -> loginError(model));
	}

	private String loginSuccess(HttpSession session, LoginResult loginResult) {
		session.setAttribute("loggedUser", loginResult.publicUser());
		session.setAttribute("authToken", loginResult.generatedToken());
		log.info("Login correcto, se añade a la sesión el token {}", loginResult.generatedToken());

		return "redirect:/home";
	}

	private String loginError(Model model) {
		model.addAttribute("loginError", "¡Las credenciales son incorrectas!");
		model.addAttribute("loginForm", new LoginFormDto());
		return "pages/access/login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
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
		if (privateUserService.isTokenRegistered(token)) {
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
		if (privateUserService.isTokenRegistered(token)) {
			// TODO - Borrar
			System.out.println("Contraseña de Usuario recibida -> 1:" + userPasswordFormDto.getPassword() + "2:" + userPasswordFormDto.getConfirmPassword());
			privateUserService.updateUserPassword(
				token,
				userPasswordFormDto.getPassword(),
				userPasswordFormDto.getConfirmPassword()
			);
		}
		return "redirect:/login";
	}
}
