package com.knowy.server.controller;

import com.knowy.server.controller.dto.UserConfigChangeEmailFormDto;
import com.knowy.server.controller.dto.UserProfileDTO;
import com.knowy.server.service.LanguageService;
import com.knowy.server.service.UserFacadeService;
import com.knowy.server.service.exception.*;
import com.knowy.server.service.model.UserSecurityDetails;
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

	private final UserFacadeService userFacadeService;
	private final LanguageService languageService;
	private final UserSecurityDetailsHelper userSecurityDetailsHelper;

	/**
	 * The constructor
	 *
	 * @param userFacadeService         the userFacadeService
	 * @param languageService           the languageService
	 * @param userSecurityDetailsHelper the userSecurityDetailsHelper
	 */
	public UserConfigController(
		UserFacadeService userFacadeService,
		LanguageService languageService,
		UserSecurityDetailsHelper userSecurityDetailsHelper
	) {
		this.userFacadeService = userFacadeService;
		this.languageService = languageService;
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
	public String updateEmail(
		@ModelAttribute UserConfigChangeEmailFormDto userConfigChangeEmailFormDto,
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		RedirectAttributes redirectAttributes
	) {
		try {
			userFacadeService.updateEmail(
				userConfigChangeEmailFormDto.getEmail(),
				userDetails.getPublicUser().getId(),
				userConfigChangeEmailFormDto.getPassword()
			);

			userSecurityDetailsHelper.refreshUserAuthenticationById();
			redirectAttributes.addFlashAttribute("successEmail", "Email actualizado con éxito.");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("errorEmail", "Usuario no encontrado.");
		} catch (UnchangedEmailException e) {
			redirectAttributes.addFlashAttribute("errorEmail", "El nuevo correo debe ser diferente al actual.");
		} catch (WrongPasswordException e) {
			redirectAttributes.addFlashAttribute("errorEmail", "La contraseña es incorrecta.");
		} catch (InvalidUserEmailException e) {
			redirectAttributes.addFlashAttribute("errorEmail", "El correo ingresado ya está asociado a una cuenta existente.");
		}
		return "redirect:/user-account";
	}

	// TODO - JavaDoc
	@GetMapping("/delete-account-advise")
	public String deleteAccountForm(ModelMap interfaceScreen, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		interfaceScreen.addAttribute("username", userDetails.getPublicUser().getNickname());
		return "pages/user-management/delete-account";
	}

	// TODO - JavaDoc
	@GetMapping("/delete-account-confirm")
	public String deleteAccountEnd(ModelMap interfaceScreen, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		interfaceScreen.addAttribute("username", userDetails.getPublicUser().getNickname());
		return "pages/user-management/delete-account-confirm";
	}

	// TODO - JavaDoc
	@PostMapping("/delete-account-confirm")
	public String deleteAccount(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@RequestParam String password,
		@RequestParam String confirmPassword,
		RedirectAttributes redirectAttributes,
		HttpServletRequest request
	) {
		String email = userDetails.getUsername();
		String domainUrl = getDomainUrl(request);
		String recoveryBaseUrl = domainUrl + "/reactivate-account";

		try {
			userFacadeService.desactivateUserAccount(password, confirmPassword, email, recoveryBaseUrl);
			redirectAttributes.addFlashAttribute(SUCCESS_MODEL_ATTRIBUTE,
				"Tu cuenta ha sido desactivada correctamente. Dispones de 30 días para recuperarla.");
			return "redirect:delete-advise";

		} catch (WrongPasswordException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "La contraseña es incorrecta o no coincide");
			return "redirect:delete-account-confirm";
		} catch (MailDispatchException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Error al enviar el email");
			return "redirect:delete-account-confirm";
		} catch (JwtKnowyException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Error al recuperar el token");
			return "redirect:delete-account-confirm";
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Usuario no encontrado");
			return "redirect:delete-account-confirm";
		}
	}

	private String getDomainUrl(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		return scheme + "://" + serverName + ":" + serverPort;
	}


	// TODO - JavaDoc
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
			model.addAttribute(ERROR_MODEL_ATTRIBUTE, "Usuario no encontrado");
			return "error/error";
		}
	}

	// TODO - JavaDoc
	@GetMapping("delete-advise")
	public String deleteAdvise() {
		return "pages/user-management/account-reactivation";
	}

	// TODO - JavaDoc
	@GetMapping("/user-profile")
	public String viewUserProfile(Model model, UserProfileDTO userProfileDTO, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		Hibernate.initialize(userDetails.getPublicUser().getLanguages());
		model.addAttribute("publicUser", userDetails.getPublicUser());
		model.addAttribute("languages", languageService.findAll());
		return "pages/user-management/user-profile";
	}

	// TODO - JavaDoc
	@PostMapping("/update-user-profile")
	public String updateUserProfile(
		@ModelAttribute("profileDto") UserProfileDTO userProfileDTO,
		RedirectAttributes redirectAttributes,
		@AuthenticationPrincipal UserSecurityDetails userDetails
	) {
		String newNickname = userProfileDTO.getNickname();
		if (newNickname != null && !newNickname.isBlank()) {
			try {
				userFacadeService.updateNickname(newNickname, userDetails.getPublicUser().getId());
				redirectAttributes.addFlashAttribute("username", newNickname);
			} catch (UserNotFoundException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Usuario no encontrado.");
				return "redirect:/user-profile";
			} catch (UnchangedNicknameException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "El nuevo nombre debe ser diferente al actual.");
				return "redirect:/user-profile";
			} catch (NicknameAlreadyTakenException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "El nombre ya está en uso.");
				return "redirect:/user-profile";
			} catch (InvalidUserNicknameException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "No se permiten apodos en blanco o vacíos.");
				return "redirect:/user-profile";
			}
		}

		if (userProfileDTO.getProfilePictureId() != null && userProfileDTO.getProfilePictureId() > 0) {
			try {
				userFacadeService.updateProfileImage(userProfileDTO.getProfilePictureId(), userDetails.getPublicUser().getId());
				redirectAttributes.addFlashAttribute("profilePicture", userProfileDTO.getProfilePictureId());
				redirectAttributes.addFlashAttribute("profilePictureUrl", userDetails.getPublicUser().getProfileImage().getUrl());
			} catch (ImageNotFoundException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Aún no existe una imagen de perfil");
				return "redirect:/user-profile";
			} catch (UnchangedImageException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "La imagen debe ser diferente a la actual.");
				return "redirect:/user-profile";
			} catch (UserNotFoundException e) {
				redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Usuario no encontrado");
				return "redirect:/user-profile";
			}
		}

		String[] newLanguages = userProfileDTO.getLanguages() != null
			? userProfileDTO.getLanguages()
			: new String[0];
		try {
			userFacadeService.updateLanguages(userDetails.getPublicUser().getId(), newLanguages);
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute(ERROR_MODEL_ATTRIBUTE, "Usuario no encontrado");
		}

		redirectAttributes.addFlashAttribute(SUCCESS_MODEL_ATTRIBUTE, "Perfil actualizado correctamente");
		redirectAttributes.addFlashAttribute("nickname", userProfileDTO.getNickname());
		redirectAttributes.addFlashAttribute("languages", userProfileDTO.getLanguages());

		userSecurityDetailsHelper.refreshUserAuthentication();
		return "redirect:/user-profile";
	}
}
