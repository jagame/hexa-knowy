package com.knowy.server.controller;

import com.knowy.server.controller.dto.UserConfigChangeEmailFormDto;
import com.knowy.server.service.UserSecurityDetailsService;
import com.knowy.server.controller.dto.SessionUser;
import com.knowy.server.controller.dto.UserConfigSessionDTO;
import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.controller.dto.UserProfileDTO;
import com.knowy.server.service.exception.*;
import com.knowy.server.service.UserService;
import com.knowy.server.service.exception.NicknameAlreadyTakenException;
import com.knowy.server.service.exception.UnchangedEmailException;
import com.knowy.server.service.exception.UnchangedNicknameException;
import com.knowy.server.service.exception.UserNotFoundException;
import com.knowy.server.service.model.UserSecurityDetails;
import com.knowy.server.util.exception.WrongPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.knowy.server.util.exception.UserNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
public class UserConfigController {

	private final UserService userService;
	private final UserSecurityDetailsService userSecurityDetailsService;

	public UserConfigController(UserService userService, UserSecurityDetailsService userSecurityDetailsService) {
		this.userService = userService;
		this.userSecurityDetailsService = userSecurityDetailsService;
	}


	/**
	 * Handles the request to view the user account page.
	 *
	 * <p>Fetches the authenticated user's public information and prepares an empty DTO
	 * for updating the email address. This data is added to the model for rendering in the view.</p>
	 *
	 * @param model       the model used to pass attributes to the view
	 * @param userDetails the authenticated user's security details injected by Spring Security
	 * @return the path to the user account view template
	 */
	@GetMapping("/user-account")
	public String viewUserAccount(Model model, HttpSession session) {
		SessionUser loggedUser = (SessionUser) session.getAttribute(SESSION_LOGGED_USER);

		String email = getCurrentEmail(session);
		Optional<PrivateUserEntity> privateUser = userService.findPrivateUserByEmail(email);
		// FixMe: Changing the way we get UserById in the future
		PublicUserEntity publicUser = null;
		try {
			publicUser = userService.findPublicUserById(loggedUser.id());
		} catch (UserNotFoundException e) {
			throw new RuntimeException(e);
		}

		if (privateUser.isEmpty()) {
			// Manejo de error: usuario no encontrado
			model.addAttribute("error", "User information could not be uploaded.");
			return "pages/user-management/user-account";
		}

		model.addAttribute("privateUser", privateUser.get());
		model.addAttribute("publicUser", publicUser);
		session.setAttribute("nickName", publicUser.getNickname());

		UserConfigSessionDTO userConfigSessionDTO = new UserConfigSessionDTO();
		userConfigSessionDTO.setEmail(privateUser.get().getEmail());
		model.addAttribute("userConfigSessionDTO", userConfigSessionDTO);
		return "pages/user-management/user-account";
	}

	private String getCurrentEmail(HttpSession session) {
		String email = (String) session.getAttribute("email");
		if (email == null) {
			email = "usuario123@correo.com";
			session.setAttribute("email", email);
			// FixMe: Changing the way we get email in the future
		}
		return email;
	}

	//Method available for use on the other pages of UserConfig
	public String getCurrentNickname(HttpSession session) throws UserNotFoundException {

		SessionUser loggedUser = (SessionUser) session.getAttribute(SESSION_LOGGED_USER);
		return userService.findNicknameById(loggedUser.id());

	}

	// * Fixed by Nico
	@PostMapping("/update-nickname")
	public String updateNickname(
		String newNickname,
		Integer id,
		RedirectAttributes redirectAttributes
	) {
		try {
			userService.updateNickname(newNickname, id);
			redirectAttributes.addFlashAttribute("success", "Nombre de usuario actualizado");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
		} catch (UnchangedNicknameException e) {
			redirectAttributes.addFlashAttribute("error", "El nuevo nombre debe ser diferente al actual.");
		} catch (NicknameAlreadyTakenException e) {
			redirectAttributes.addFlashAttribute("error", "El nombre ya está en uso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error inesperado al actualizar el nombre.");
		}
		return "redirect:/user-account";
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
			userService.updateEmail(
				userConfigChangeEmailFormDto.getEmail(),
				userDetails.getPublicUser().getId(),
				userConfigChangeEmailFormDto.getPassword()
			);

			userSecurityDetailsService.refreshUserAuthenticationById();
			redirectAttributes.addFlashAttribute("successEmail", "Email actualizado con éxito.");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("errorEmail", "Usuario no encontrado.");
		} catch (UnchangedEmailException e) {
			redirectAttributes.addFlashAttribute("errorEmail", "El nuevo correo debe ser diferente al actual.");
		} catch (WrongPasswordException e) {
			redirectAttributes.addFlashAttribute("errorEmail", "La contraseña es incorrecta.");
		}
		return "redirect:/user-account";
	}

	// Delete account
	@GetMapping("/delete-account")
	public String deleteAccountForm(ModelMap interfaceScreen, HttpSession session) throws UserNotFoundException {
		try {
			interfaceScreen.addAttribute("username", getCurrentNickname(session));
		} catch (UserNotFoundException e) {
			throw new UserNotFoundException("Usuario en sesión no encontrado: id=" + session.getId());
		}
		return "pages/user-management/delete-account";
	}

	//Delete-Account-End (Finally deleting Account)
	@GetMapping("/delete-account-end")
	public String deleteAccountEnd(ModelMap interfaceScreen, HttpSession session) throws UserNotFoundException {
		interfaceScreen.addAttribute("username", getCurrentNickname(session));
		return "pages/user-management/delete-account-end";
	}

	//User-Profile
	@GetMapping("/user-profile")
	public String viewUserProfile(Model model, UserProfileDTO userProfileDTO, HttpSession session) throws UserNotFoundException {
		SessionUser loggedUser = (SessionUser) session.getAttribute(SESSION_LOGGED_USER);
//		log.info(userProfileDTO.toString()); //Fixme - Eventualmente lo quitaremos

		model.addAttribute("userProfileDTO", userProfileDTO);
		model.addAttribute("username", userProfileDTO.getNickname());
		model.addAttribute("currentNickname", getCurrentNickname(session));
		model.addAttribute("profilePictureUrl", userService.getProfileImageUrlById(loggedUser.id()));
		return "pages/user-management/user-profile";
	}

	@PostMapping("/update-user-profile")
	public String updateUserProfile(@ModelAttribute("profileDto") UserProfileDTO userProfileDTO, RedirectAttributes redirectAttributes, HttpSession session) throws UserNotFoundException {
		SessionUser loggedUser = (SessionUser) session.getAttribute(SESSION_LOGGED_USER);
		log.info(userProfileDTO.toString()); //Fixme - Eventualmente lo quitaremos

		String newNickname = userProfileDTO.getNickname();
		if (newNickname != null && !newNickname.isBlank()) {
			try {
				userService.updateNickname(newNickname, loggedUser.id());
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
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("error", "Error inesperado al actualizar el nombre.");
				return "redirect:/user-profile";
			}
		}

		if (userProfileDTO.getProfilePictureId() != null && userProfileDTO.getProfilePictureId() > 0) {
			try {
				userService.updateProfileImage(userProfileDTO.getProfilePictureId(), loggedUser.id());
				redirectAttributes.addFlashAttribute("profilePicture", userProfileDTO.getProfilePictureId());
				redirectAttributes.addFlashAttribute("profilePictureUrl", userService.getProfileImageUrlById(loggedUser.id()));
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

		String[] newLanguages = userProfileDTO.getLanguages();
		try {
			userService.updateLanguages(loggedUser.id(), newLanguages);
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
		}

		redirectAttributes.addFlashAttribute("success", "Perfil actualizado correctamente");
		redirectAttributes.addFlashAttribute("nickname", userProfileDTO.getNickname());
		redirectAttributes.addFlashAttribute("languages", userProfileDTO.getLanguages());

		return "redirect:/user-profile";
	}

}
