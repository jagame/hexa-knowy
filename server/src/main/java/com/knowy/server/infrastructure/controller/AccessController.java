package com.knowy.server.infrastructure.controller;

import com.knowy.server.application.domain.UserPrivate;
import com.knowy.server.application.exception.KnowyMailDispatchException;
import com.knowy.server.application.exception.KnowyWrongPasswordException;
import com.knowy.server.application.service.UserFacadeService;
import com.knowy.server.application.service.exception.KnowyImageNotFoundException;
import com.knowy.server.application.service.exception.KnowyInvalidUserException;
import com.knowy.server.application.service.exception.KnowyUserNotFoundException;
import com.knowy.server.infrastructure.controller.dto.LoginFormDto;
import com.knowy.server.infrastructure.controller.dto.UserEmailFormDto;
import com.knowy.server.infrastructure.controller.dto.UserPasswordFormDto;
import com.knowy.server.infrastructure.controller.dto.UserRegisterFormDto;
import com.knowy.server.util.UserSecurityDetailsHelper;
import com.knowy.server.application.exception.KnowyTokenException;
import com.knowy.server.application.exception.KnowyPasswordFormatException;
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

	private static final String ERROR_MODEL_ATTRIBUTE = "error";
	private static final String LOGIN_REDIRECT_URL = "redirect:/login";

	private final UserSecurityDetailsHelper userSecurityDetailsHelper;
	private final UserFacadeService userFacadeService;

	/**
	 * The constructor
	 *
	 * @param userFacadeService         the accessService
	 * @param userSecurityDetailsHelper the userSecurityDetailsService
	 */
	public AccessController(UserFacadeService userFacadeService, UserSecurityDetailsHelper userSecurityDetailsHelper) {
		this.userFacadeService = userFacadeService;
		this.userSecurityDetailsHelper = userSecurityDetailsHelper;
	}

	/**
	 * Handles GET requests for the login page.
	 * <p>
	 * Adds an empty {@link LoginFormDto} object to the model, which will be used to bind the login form data in the
	 * view.
	 * </p>
	 *
	 * @return the name of the login view template
	 */
	@GetMapping("/login")
	public String viewLogin() {
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
	 * Handles the user registration form submission via POST.
	 * <p>
	 * This method validates the submitted {@link UserRegisterFormDto} and checks for binding errors. If validation
	 * errors are found, it redirects back to the registration page with the first error message. If the form is valid,
	 * it attempts to register a new user and automatically logs them in. In case of registration failure (e.g., due to
	 * invalid user data), it redirects back to the registration page with an appropriate error message.
	 * </p>
	 *
	 * @param user               the registration form data bound to {@link UserRegisterFormDto}, validated
	 *                           automatically
	 * @param redirectAttributes used to add flash attributes (such as error messages) during redirect
	 * @param errors             holds validation and binding errors for the submitted form
	 * @return a redirect URL string: either back to "/register" on error or to "/home" on successful registration
	 */
	@PostMapping("/register")
	public String processRegisterForm(
		@Valid @ModelAttribute UserRegisterFormDto user,
		RedirectAttributes redirectAttributes,
		Errors errors
	) throws KnowyImageNotFoundException {
		try {
			validateFieldErrors(errors);

			UserPrivate userPrivate = userFacadeService.registerNewUser(
				user.getNickname(),
				user.getEmail(),
				user.getPassword()
			);

			userSecurityDetailsHelper.autoLoginUserByEmail(userPrivate.email());
			return "redirect:/home";
		} catch (KnowyInvalidUserException e) {
			redirectAttributes.addFlashAttribute("user", user);
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, e.getMessage());
			return "redirect:/register";
		}
	}

	private void validateFieldErrors(Errors errors) throws KnowyInvalidUserException {
		FieldError firstError = errors.getFieldError();
		if (firstError == null) {
			return;
		}

		String message = firstError.getDefaultMessage();
		if (message == null || message.isEmpty()) {
			throw new KnowyInvalidUserException("Hubo un problema con la información proporcionada. Por favor, revise los " + "campos y vuelva a intentarlo.");
		}
		throw new KnowyInvalidUserException(message);
	}


	/**
	 * Handles GET requests to display the password change email form.
	 *
	 * @return the name of the view for the password change email page
	 */
	@GetMapping("/password-change/email")
	public String passwordChangeEmail() {
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
			userFacadeService.sendRecoveryPasswordEmail(email.getEmail(), getPasswordChangeUrl(httpServletRequest));
			return LOGIN_REDIRECT_URL;
		} catch (KnowyUserNotFoundException | KnowyTokenException | KnowyMailDispatchException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Se ha producido un error al enviar el email. Intente lo más tarde");
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
	public String passwordChange(@RequestParam String token, Model model) throws KnowyUserNotFoundException {
		if (!userFacadeService.isValidToken(token)) {
			return "redirect:/";
		}
		model.addAttribute("token", token);
		return "pages/access/password-change";
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
			userFacadeService.updatePassword(token, userPasswordFormDto.getPassword(), userPasswordFormDto.getConfirmPassword());
			return LOGIN_REDIRECT_URL;
		} catch (KnowyUserNotFoundException | KnowyTokenException e) {
			redirectAttributes.addAttribute(ERROR_MODEL_ATTRIBUTE, "Se ha producido un error al actualizar la contraseña");
			return LOGIN_REDIRECT_URL;
		} catch (KnowyPasswordFormatException e) {
			redirectAttributes.addAttribute(ERROR_MODEL_ATTRIBUTE, """
				Formato de contraseña inválido. Debe tener al menos:
				- 8 caracteres
				- 1 mayúscula, 1 minúscula
				- 1 número y 1 símbolo
				- Sin espacios
				""");
			return "redirect:/password-change?token=" + token;
		} catch (KnowyWrongPasswordException e) {
			redirectAttributes.addAttribute(ERROR_MODEL_ATTRIBUTE, "Las contraseñas no coinciden");
			return "redirect:/password-change?token=" + token;
		}
	}
}