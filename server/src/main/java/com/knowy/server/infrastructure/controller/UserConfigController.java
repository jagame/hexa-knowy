package com.knowy.server.infrastructure.controller;

import com.knowy.server.application.service.exception.*;
import com.knowy.server.infrastructure.controller.dto.UserConfigChangeEmailFormDto;
import com.knowy.server.infrastructure.controller.dto.UserProfileDTO;
import com.knowy.server.application.service.CategoryService;
import com.knowy.server.application.service.UserFacadeService;
import com.knowy.server.application.service.model.UserSecurityDetails;
import com.knowy.server.util.UserSecurityDetailsHelper;
import com.knowy.server.util.exception.JwtKnowyException;
import com.knowy.server.util.exception.MailDispatchException;
import com.knowy.server.util.exception.WrongPasswordException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class UserConfigController {

	private static final String ERROR_MODEL_ATTRIBUTE = "error";
	private static final String SUCCESS_MODEL_ATTRIBUTE = "success";
	private static final String USERNAME_MODEL_ATTRIBUTE = "username";
	private static final String DELETE_ACCOUNT_CONFIRM_REDIRECT_URL = "redirect:delete-account-confirm";
	private static final String USER_NOT_FOUND_ERROR_MESSAGE = "Usuario no encontrado";

	private final UserFacadeService userFacadeService;
	private final CategoryService categoryService;
	private final UserSecurityDetailsHelper userSecurityDetailsHelper;

	/**
	 * The constructor
	 *
	 * @param userFacadeService         the userFacadeService
	 * @param categoryService           the languageService
	 * @param userSecurityDetailsHelper the userSecurityDetailsHelper
	 */
	public UserConfigController(UserFacadeService userFacadeService, CategoryService categoryService, UserSecurityDetailsHelper userSecurityDetailsHelper) {
		this.userFacadeService = userFacadeService;
		this.categoryService = categoryService;
		this.userSecurityDetailsHelper = userSecurityDetailsHelper;
	}

	/**
	 * Displays the user account page.
	 *
	 * <p>Retrieves the authenticated user's public information and adds it to the model.
	 * Prepares the necessary data for the view to render the user's account details.</p>
	 *
	 * @param model       the model to which attributes are added for rendering the view
	 * @param userDetails the authenticated user's security details provided by Spring Security
	 * @return the name of the view template for the user account page
	 */
	@GetMapping("/user-account")
	public String viewUserAccount(Model model, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		model.addAttribute("publicUser", userDetails.getPublicUser());
		return "pages/user-management/user-account";
	}

	/**
	 * Handles the request to update a user's email address.
	 *
	 * <p>This method receives the new email and current password, verifies the user's identity,
	 * and attempts to update the email address. Proper error handling is applied for invalid inputs or mismatches.</p>
	 *
	 * <p>On success or failure, an appropriate flash message is added for display on redirect.</p>
	 *
	 * @param userConfigChangeEmailFormDto the form object containing the new email and current password
	 * @param userDetails                  the authenticated user's security details, used to retrieve the user ID
	 * @param redirectAttributes           used to pass flash messages after redirect
	 * @return a redirect to the user account page
	 */
	@PostMapping("/update-email")
	public String updateEmail(@ModelAttribute UserConfigChangeEmailFormDto userConfigChangeEmailFormDto, @AuthenticationPrincipal UserSecurityDetails userDetails, RedirectAttributes redirectAttributes) {
		try {
			userFacadeService.updateEmail(userConfigChangeEmailFormDto.getEmail(), userDetails.getPublicUser().getId(), userConfigChangeEmailFormDto.getPassword());

			userSecurityDetailsHelper.refreshUserAuthenticationById();
			redirectAttributes.addFlashAttribute(SUCCESS_MODEL_ATTRIBUTE, "Email actualizado con éxito.");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Usuario no encontrado.");
		} catch (UnchangedEmailException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "El nuevo correo debe ser diferente al actual.");
		} catch (WrongPasswordException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "La contraseña es incorrecta.");
		} catch (InvalidUserEmailException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "El correo ingresado ya está asociado a una cuenta existente.");
		}
		return "redirect:/user-account";
	}

	/**
	 * Displays the account deletion advice page with the current user's nickname.
	 *
	 * @param interfaceScreen the model map to pass attributes to the view
	 * @param userDetails     the authenticated user's security details
	 * @return the view name for the account deletion advice page
	 */
	@GetMapping("/delete-account-advise")
	public String deleteAccountForm(ModelMap interfaceScreen, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		interfaceScreen.addAttribute(USERNAME_MODEL_ATTRIBUTE, userDetails.getPublicUser().getNickname());
		return "pages/user-management/delete-account";
	}

	/**
	 * Displays the account deletion confirmation page with the current user's nickname.
	 *
	 * @param interfaceScreen the model map to pass attributes to the view
	 * @param userDetails     the authenticated user's security details
	 * @return the view name for the account deletion confirmation page
	 */
	@GetMapping("/delete-account-confirm")
	public String deleteAccountEnd(ModelMap interfaceScreen, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		interfaceScreen.addAttribute(USERNAME_MODEL_ATTRIBUTE, userDetails.getPublicUser().getNickname());
		return "pages/user-management/delete-account-confirm";
	}

	/**
	 * Handles the POST request to confirm account deletion. Validates the provided password and confirmation password,
	 * deactivates the user account if valid, and initiates the recovery email process. On success, redirects to the
	 * deletion advice page with a success message. On failure, redirects back to the confirmation page with an
	 * appropriate error message.
	 *
	 * @param userDetails        the authenticated user's security details
	 * @param password           the password provided by the user for confirmation
	 * @param confirmPassword    the confirmation of the password
	 * @param redirectAttributes used to pass flash attributes during redirect
	 * @param request            the HTTP servlet request to build domain URL
	 * @return redirect URL to either the deletion advice page on success or back to the confirmation page on failure
	 */
	@PostMapping("/delete-account-confirm")
	public String deleteAccount(@AuthenticationPrincipal UserSecurityDetails userDetails, @RequestParam String password, @RequestParam String confirmPassword, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String email = userDetails.getUsername();
		String domainUrl = getDomainUrl(request);
		String recoveryBaseUrl = domainUrl + "/reactivate-account";

		try {
			userFacadeService.desactivateUserAccount(password, confirmPassword, email, recoveryBaseUrl);
			redirectAttributes.addFlashAttribute(SUCCESS_MODEL_ATTRIBUTE, "Tu cuenta ha sido desactivada correctamente. Dispones de 30 días para recuperarla.");
			return "redirect:delete-advise";

		} catch (WrongPasswordException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "La contraseña es incorrecta o no coincide");
			return DELETE_ACCOUNT_CONFIRM_REDIRECT_URL;
		} catch (MailDispatchException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Error al enviar el email");
			return DELETE_ACCOUNT_CONFIRM_REDIRECT_URL;
		} catch (JwtKnowyException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Error al recuperar el token");
			return DELETE_ACCOUNT_CONFIRM_REDIRECT_URL;
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, USER_NOT_FOUND_ERROR_MESSAGE);
			return DELETE_ACCOUNT_CONFIRM_REDIRECT_URL;
		}
	}

	private String getDomainUrl(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		return scheme + "://" + serverName + ":" + serverPort;
	}


	/**
	 * Handles the account reactivation process using a token. Attempts to reactivate the user account linked to the
	 * provided token. On success, shows a success message and returns the reactivation confirmation page. On failure,
	 * shows an error message and returns an error page.
	 *
	 * @param token the reactivation token provided as a request parameter
	 * @param model the Spring MVC model to pass attributes to the view
	 * @return the name of the view to be rendered
	 */
	@GetMapping("/reactivate-account")
	public String reactivateAccount(@RequestParam("token") String token, Model model) {

		try {
			userFacadeService.reactivateUserAccount(token);
			model.addAttribute(SUCCESS_MODEL_ATTRIBUTE, "Tu cuenta ha sido reactivada correctamente.");
			return "pages/user-management/account-reactivation";

		} catch (JwtKnowyException e) {
			model.addAttribute(ERROR_MODEL_ATTRIBUTE, "El token ha expirado o no es válido");
			return "error/error";
		} catch (UserNotFoundException e) {
			model.addAttribute(ERROR_MODEL_ATTRIBUTE, USER_NOT_FOUND_ERROR_MESSAGE);
			return "error/error";
		}
	}

	/**
	 * Displays the deletion advice page after account deactivation.
	 *
	 * @return the name of the deletion advice view page
	 */
	@GetMapping("delete-advise")
	public String deleteAdvise() {
		return "pages/user-management/account-reactivation";
	}

	/**
	 * Displays the user profile page. Initializes the user's categories collection to avoid lazy loading issues. Adds
	 * the public user information and list of all available categories to the model.
	 *
	 * @param model          the Spring MVC model to pass attributes to the view
	 * @param userProfileDTO DTO object representing user profile data (used for form binding)
	 * @param userDetails    the authenticated user's security details
	 * @return the name of the user profile view page
	 */
	@GetMapping("/user-profile")
	public String viewUserProfile(Model model, UserProfileDTO userProfileDTO, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		Hibernate.initialize(userDetails.getPublicUser().getLanguages());
		model.addAttribute("publicUser", userDetails.getPublicUser());
		model.addAttribute("categories", categoryService.findAll());
		return "pages/user-management/user-profile";
	}

	/**
	 * Handles the update of the user's profile, including nickname, profile picture, and categories. Validates and
	 * applies changes to the user's data by invoking the corresponding service methods. Adds appropriate success or
	 * error messages as flash attributes for feedback in the redirected view. Refreshes the user's authentication
	 * details after a successful update.
	 *
	 * @param userProfileDTO     the DTO containing the updated profile data from the form
	 * @param redirectAttributes attributes for flash messages to be used after redirect
	 * @param userDetails        the authenticated user's security details
	 * @return the redirect URL to the user profile page
	 */
	@PostMapping("/update-user-profile")
	public String updateUserProfile(
		@ModelAttribute("profileDto") UserProfileDTO userProfileDTO,
		RedirectAttributes redirectAttributes,
		@AuthenticationPrincipal UserSecurityDetails userDetails) {

		Integer userId = userDetails.getPublicUser().getId();

		updateNickname(userProfileDTO.getNickname(), userId, redirectAttributes);
		updateProfileImage(userProfileDTO.getProfilePictureId(), userDetails, userId, redirectAttributes);
		updateLanguages(userProfileDTO.getLanguages(), userId, redirectAttributes);

		redirectAttributes.addFlashAttribute(SUCCESS_MODEL_ATTRIBUTE, "Perfil actualizado correctamente");
		redirectAttributes.addFlashAttribute("nickname", userProfileDTO.getNickname());
		redirectAttributes.addFlashAttribute("categories", userProfileDTO.getLanguages());

		userSecurityDetailsHelper.refreshUserAuthentication();
		return "redirect:/user-profile";
	}

	private void updateNickname(
		String newNickname,
		Integer userId,
		RedirectAttributes redirectAttributes
	) {
		if (newNickname != null && !newNickname.isBlank()) {
			try {
				userFacadeService.updateNickname(newNickname, userId);
				redirectAttributes.addFlashAttribute(USERNAME_MODEL_ATTRIBUTE, newNickname);
			} catch (UserNotFoundException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Usuario no encontrado.");
			} catch (UnchangedNicknameException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "El nuevo nombre debe ser diferente al actual.");
			} catch (NicknameAlreadyTakenException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "El nombre ya está en uso.");
			} catch (InvalidUserNicknameException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "No se permiten apodos en blanco o vacíos.");
			}
		}
	}

	private void updateProfileImage(
		Integer profilePictureId, UserSecurityDetails userDetails,
		int userId,
		RedirectAttributes redirectAttributes
	) {
		if (profilePictureId != null && profilePictureId > 0) {
			try {
				userFacadeService.updateProfileImage(profilePictureId, userId);
				redirectAttributes.addFlashAttribute("profilePicture", profilePictureId);
				redirectAttributes.addFlashAttribute("profilePictureUrl", userDetails.getPublicUser().getProfileImage().getUrl());
			} catch (ImageNotFoundException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Aún no existe una imagen de perfil");
			} catch (UnchangedImageException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "La imagen debe ser diferente a la actual.");
			} catch (UserNotFoundException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, USER_NOT_FOUND_ERROR_MESSAGE);
			}
		}
	}

	private void updateLanguages(String[] languages, int userId, RedirectAttributes redirectAttributes) {
		String[] newLanguages = languages != null ? languages : new String[0];
		try {
			userFacadeService.updateLanguages(userId, newLanguages);
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, USER_NOT_FOUND_ERROR_MESSAGE);
		}
	}

}
