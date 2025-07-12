package com.knowy.server.controller;

import com.knowy.server.controller.dto.UserConfigChangeEmailFormDto;
import com.knowy.server.controller.dto.UserProfileDTO;
import com.knowy.server.service.LanguageService;
import com.knowy.server.service.UserFacadeService;
import com.knowy.server.service.exception.*;
import com.knowy.server.service.model.UserSecurityDetails;
import com.knowy.server.util.UserSecurityDetailsHelper;
import com.knowy.server.util.exception.WrongPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class UserConfigController {

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

	// Delete account
	@GetMapping("/delete-account")
	public String deleteAccountForm(ModelMap interfaceScreen, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		interfaceScreen.addAttribute("username", userDetails.getPublicUser().getNickname());
		return "pages/user-management/delete-account";
	}

	//Delete-Account-End (Finally deleting Account)
	@GetMapping("/delete-account-end")
	public String deleteAccountEnd(ModelMap interfaceScreen, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		interfaceScreen.addAttribute("username", userDetails.getPublicUser().getNickname());
		return "pages/user-management/delete-account-end";
	}

	//User-Profile
	@GetMapping("/user-profile")
	public String viewUserProfile(Model model, UserProfileDTO userProfileDTO, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		Hibernate.initialize(userDetails.getPublicUser().getLanguages());
		model.addAttribute("publicUser", userDetails.getPublicUser());
		model.addAttribute("languages", languageService.findAll());
		return "pages/user-management/user-profile";
	}

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
				redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
				return "redirect:/user-profile";
			} catch (UnchangedNicknameException e) {
				redirectAttributes.addFlashAttribute("error", "El nuevo nombre debe ser diferente al actual.");
				return "redirect:/user-profile";
			} catch (NicknameAlreadyTakenException e) {
				redirectAttributes.addFlashAttribute("error", "El nombre ya está en uso.");
				return "redirect:/user-profile";
			} catch (InvalidUserNicknameException e) {
				redirectAttributes.addFlashAttribute("error", "No se permiten apodos en blanco o vacíos.");
				return "redirect:/user-profile";
			}
		}

		if (userProfileDTO.getProfilePictureId() != null && userProfileDTO.getProfilePictureId() > 0) {
			try {
				userFacadeService.updateProfileImage(userProfileDTO.getProfilePictureId(), userDetails.getPublicUser().getId());
				redirectAttributes.addFlashAttribute("profilePicture", userProfileDTO.getProfilePictureId());
				redirectAttributes.addFlashAttribute("profilePictureUrl", userDetails.getPublicUser().getProfileImage().getUrl());
			} catch (ImageNotFoundException e) {
				redirectAttributes.addFlashAttribute("error", "Aún no existe una imagen de perfil");
				return "redirect:/user-profile";
			} catch (UnchangedImageException e) {
				redirectAttributes.addFlashAttribute("error", "La imagen debe ser diferente a la actual.");
				return "redirect:/user-profile";
			} catch (UserNotFoundException e) {
				redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
				return "redirect:/user-profile";
			}
		}

		String[] newLanguages = userProfileDTO.getLanguages() != null
			? userProfileDTO.getLanguages()
			: new String[0];
		try {
			userFacadeService.updateLanguages(userDetails.getPublicUser().getId(), newLanguages);
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
		}

		redirectAttributes.addFlashAttribute("success", "Perfil actualizado correctamente");
		redirectAttributes.addFlashAttribute("nickname", userProfileDTO.getNickname());
		redirectAttributes.addFlashAttribute("languages", userProfileDTO.getLanguages());

		userSecurityDetailsHelper.refreshUserAuthentication();
		return "redirect:/user-profile";
	}
}
