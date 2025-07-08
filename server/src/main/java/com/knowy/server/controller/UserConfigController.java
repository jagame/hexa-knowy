package com.knowy.server.controller;

import com.knowy.server.controller.dto.UserConfigChangeEmailFormDto;
import com.knowy.server.service.UserSecurityDetailsService;
import com.knowy.server.service.UserService;
import com.knowy.server.service.exception.NicknameAlreadyTakenException;
import com.knowy.server.service.exception.UnchangedEmailException;
import com.knowy.server.service.exception.UnchangedNicknameException;
import com.knowy.server.service.exception.UserNotFoundException;
import com.knowy.server.service.model.UserSecurityDetails;
import com.knowy.server.util.exception.WrongPasswordException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserConfigController {

	private final UserService userService;
	private final UserSecurityDetailsService userSecurityDetailsService;

	public UserConfigController(UserService userService, UserSecurityDetailsService userSecurityDetailsService) {
		this.userService = userService;
		this.userSecurityDetailsService = userSecurityDetailsService;
	}

	// * Fixed by Nico
	//User-Profile
	@GetMapping("/user-profile")
	public String viewUserProfile(Model model) {
		model.addAttribute("username", "usuario123");
		return "pages/user-management/user-profile";
	}

	// * Fixed by Nico
	@PostMapping("/update-Nickname")
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
	public String viewUserAccount(Model model, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		model.addAttribute("publicUser", userDetails.getPublicUser());
		model.addAttribute("userConfigChangeEmailFormDto", new UserConfigChangeEmailFormDto());
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
			userService.updateEmail(
				userConfigChangeEmailFormDto.getEmail(),
				userDetails.getPublicUser().getId(),
				userConfigChangeEmailFormDto.getPassword()
			);

			redirectAttributes.addFlashAttribute("successEmail", "Email actualizado con éxito.");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("errorEmail", "Usuario no encontrado.");
		} catch (UnchangedEmailException e) {
			redirectAttributes.addFlashAttribute("errorEmail", "El nuevo correo debe ser diferente al actual.");
		} catch (WrongPasswordException e) {
			redirectAttributes.addFlashAttribute("errorEmail", "La contraseña es incorrecta.");
		}

		userSecurityDetailsService.refreshUserAuthentication(); // ?
		return "redirect:/user-account";
	}

	// Delete account
	@GetMapping("/delete-account")
	public String deleteAccountForm(ModelMap interfaceScreen) {
		interfaceScreen.addAttribute("username", "usuario123");
		return "pages/user-management/delete-account";
	}

	//Delete-Account-End (Finally deleting Account)
	@GetMapping("/delete-account-end")
	public String deleteAccountEnd(ModelMap interfaceScreen) {
		interfaceScreen.addAttribute("username", "usuario123");
		return "pages/user-management/delete-account-end";
	}


}
