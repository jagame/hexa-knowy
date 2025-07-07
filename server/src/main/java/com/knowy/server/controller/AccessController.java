package com.knowy.server.controller;

import com.knowy.server.controller.dto.LoginFormDto;
import com.knowy.server.controller.dto.UserEmailFormDto;
import com.knowy.server.controller.dto.UserPasswordFormDto;
import com.knowy.server.controller.dto.UserRegisterFormDto;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.service.AccessService;
import com.knowy.server.service.UserSecurityDetailsService;
import com.knowy.server.service.exception.AccessException;
import com.knowy.server.service.exception.InvalidUserException;
import com.knowy.server.util.exception.PasswordFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class AccessController {

	public static final String SESSION_LOGGED_USER = "loggedUser";
	private final UserSecurityDetailsService userSecurityDetailsService;
	private final AccessService accessService;

	/**
	 * The constructor
	 *
	 * @param accessService              the accessService
	 * @param userSecurityDetailsService the userSecurityDetailsService
	 */
	public AccessController(AccessService accessService, UserSecurityDetailsService userSecurityDetailsService) {
		this.accessService = accessService;
		this.userSecurityDetailsService = userSecurityDetailsService;
	}

	/**
	 * Handles GET requests for the login page.
	 * <p>
	 * Adds an empty {@link LoginFormDto} object to the model, which will be used to bind the login form data in the
	 * view.
	 * </p>
	 *
	 * @param model the model to which attributes are added for the view rendering
	 * @return the name of the login view template
	 */
	@GetMapping("/login")
	public String viewLogin(Model model) {
		model.addAttribute("loginForm", new LoginFormDto());
		return "pages/access/login";
	}

	/**
	 * Handles GET requests for the registration page.
	 * <p>
	 * Adds an empty {@link UserRegisterFormDto} object to the model, which will be used to bind the user registration
	 * form data in the view.
	 * </p>
	 *
	 * @param model the model to which attributes are added for the view rendering
	 * @return the name of the registration view template
	 */
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new UserRegisterFormDto());
		return "pages/access/register";
	}

	/**
	 * Processes the user registration form submitted via POST.
	 * <p>
	 * Validates the submitted {@link UserRegisterFormDto}, and if validation errors exist, redirects back to the
	 * registration page with the first error message. If the form is valid, attempts to register a new user and
	 * performs automatic login. In case of registration failure (e.g., invalid user data), redirects back to the
	 * registration page with an error message.
	 * </p>
	 *
	 * @param user               the form data bound to {@link UserRegisterFormDto}, validated automatically
	 * @param redirectAttributes used to pass flash attributes (such as error messages) during redirect
	 * @param errors             contains validation errors from binding the form data
	 * @return the redirect URL, either back to registration on error or to home on success
	 */
	@PostMapping("/register")
	public String procesarFormulario(
		@Valid @ModelAttribute UserRegisterFormDto user,
		RedirectAttributes redirectAttributes,
		Errors errors
	) {
		FieldError firstError = errors.getFieldError();
		if (firstError != null && firstError.getDefaultMessage() != null) {
			redirectAttributes.addFlashAttribute("error", firstError.getDefaultMessage());
			return "redirect:/register";
		}

		try {
			PublicUserEntity publicUser = accessService.registerNewUser(user.getNickname(), user.getEmail(), user.getPassword());
			userSecurityDetailsService.autoLoginUserByEmail(publicUser.getPrivateUserEntity().getEmail());
			return "redirect:/home";
		} catch (InvalidUserException e) {
			redirectAttributes.addFlashAttribute("user", user);
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/register";
		}
	}

	/**
	 * Handles GET requests to display the password change email form.
	 *
	 * @param model the model to which the email form DTO is added
	 * @return the name of the view for the password change email page
	 */
	@GetMapping("/password-change/email")
	public String passwordChangeEmail(Model model) {
		model.addAttribute("emailForm", new UserEmailFormDto());
		return "pages/access/password-change-email";
	}

	/**
	 * Handles the POST request to initiate a password change by sending a recovery email.
	 *
	 * @param email              the form object containing the user's email address
	 * @param redirectAttributes attributes used to pass flash messages during redirect
	 * @param httpServletRequest the HTTP servlet request object, used to build the password change URL
	 * @return a redirect string to either the login page on success or back to the email form on failure
	 */
	@PostMapping("/password-change/email")
	public String passwordChangeEmail(
		@ModelAttribute("emailForm") UserEmailFormDto email,
		RedirectAttributes redirectAttributes,
		HttpServletRequest httpServletRequest
	) {
		try {
			accessService.sendRecoveryPasswordEmail(email.getEmail(), getPasswordChangeUrl(httpServletRequest));
			return "redirect:/login";
		} catch (AccessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/password-change/email";
		}
	}


	private String getPasswordChangeUrl(HttpServletRequest httpServletRequest) {
		return getDomainUrl(httpServletRequest) + "/password-change";
	}

	private String getDomainUrl(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();

		return scheme + "://" + serverName + ":" + serverPort;
	}

	/**
	 * Handles GET requests to display the password change form if the token is valid.
	 *
	 * @param token the token used to verify the password change request
	 * @param model the model to which the token and password form DTO are added
	 * @return the name of the password change view if the token is registered, otherwise redirects to the home page
	 */
	@GetMapping("/password-change")
	public String passwordChange(
		@RequestParam String token,
		Model model
	) {
		if (accessService.isValidToken(token)) {
			model.addAttribute("token", token);
			model.addAttribute("passwordForm", new UserPasswordFormDto());
			return "pages/access/password-change";
		}
		return "redirect:/";
	}

	/**
	 * Handles POST requests to update a user's password as part of a password reset flow.
	 *
	 * <p>This endpoint expects a valid JWT token and a form containing the new password and its confirmation.
	 * If the token is valid and all validations pass, the user's password is updated and the user is redirected to the
	 * login page.</p>
	 *
	 * <p>In case of failure (e.g., invalid token, mismatched passwords, or attempt to reuse the old password),
	 * the user is still redirected to the login page, and an error message can be passed via
	 * {@link RedirectAttributes}.</p>
	 *
	 * @param token               the JWT token used to authorize the password change request
	 * @param userPasswordFormDto the form DTO containing the new password and its confirmation
	 * @param redirectAttributes  attributes used to pass query parameters or flash messages during redirection
	 * @return a redirect string to the login page, whether the operation succeeds or fails
	 */
	@PostMapping("/password-change")
	public String passwordChange(
		@RequestParam("token") String token,
		@ModelAttribute("passwordForm") UserPasswordFormDto userPasswordFormDto,
		RedirectAttributes redirectAttributes
	) {
		try {
			accessService.updateUserPassword(
				token,
				userPasswordFormDto.getPassword(),
				userPasswordFormDto.getConfirmPassword()
			);
			log.info("User password updated");
			return "redirect:/login";
		} catch (AccessException e) {
			redirectAttributes.addAttribute("error", "Se ha producido un error al actualizar la contraseña");
			log.error("Failed to update user password", e);
			return "redirect:/login";
		} catch (PasswordFormatException e) {
			redirectAttributes.addAttribute("error", """
				Formato de contraseña inválido. Debe tener al menos:
				- 8 caracteres
				- 1 mayúscula, 1 minúscula
				- 1 número y 1 símbolo
				- Sin espacios
				""");
			log.error("Invalid password format", e);
			return "redirect:/password-change?token=" + token;
		}
	}
}