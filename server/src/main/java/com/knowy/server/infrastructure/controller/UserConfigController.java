package com.knowy.server.infrastructure.controller;

import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.domain.error.KnowyException;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.PublicUser;
import com.knowy.server.application.service.PrivateUserService;
import com.knowy.server.application.service.PublicUserService;
import com.knowy.server.application.service.usecase.update.email.KnowyUserEmailUpdateException;
import com.knowy.server.infrastructure.controller.dto.UserConfigSessionDTO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@Slf4j
public class UserConfigController {

	private final PublicUserService publicUserService;
	private final PrivateUserService privateUserService;
	String username = "usuario123";

	public UserConfigController(PublicUserService publicUserService, PrivateUserService privateUserService) {
		this.publicUserService = publicUserService;
		this.privateUserService = privateUserService;
	}

	//User-Profile
	@GetMapping("/user-profile")
	public String viewUserProfile(Model model) {
		model.addAttribute("username", username);
		return "pages/user-management/user-profile";
	}

	//User-Account
	@GetMapping("/user-account")
	public String viewUserAccount(Model model, HttpSession session) {
		String email = getCurrentEmail(session);

		Optional<PrivateUser> privateUser;
		try {
			privateUser = privateUserService.findPrivateUserByEmail(email);
		} catch (KnowyException e) {
			log.error("An error occurs while finding user information", e);
			model.addAttribute("error", "An error occur while finding user information");
			return "pages/user-management/user-account";
		}

		if (privateUser.isEmpty()) {
			// Manejo de error: usuario no encontrado
			model.addAttribute("error", "User information could not be uploaded.");
			return "pages/user-management/user-account";
		}

		model.addAttribute("privateUser", privateUser.get());
		model.addAttribute("publicUser", privateUser.get());
		session.setAttribute("nickName", privateUser.get().nickname());

		UserConfigSessionDTO userConfigSessionDTO = new UserConfigSessionDTO();
		userConfigSessionDTO.setEmail(privateUser.get().email());
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
	private String getCurrentNickname(HttpSession session) throws KnowyException {
		String nickname = (String) session.getAttribute("nickname");
		if (nickname == null) {
			// FixMe: Changing the way we get UserById in the future
			Optional<PublicUser> publicUser = publicUserService.findPublicUserById(3);
			publicUser.ifPresent(publicUserEntity -> session.setAttribute("nickname", publicUserEntity.nickname()));
		}
		return nickname;
	}

	@PostMapping("/update-Nickname")
	public String updateNickname(String newNickname, Integer id,
								 RedirectAttributes redirectAttributes,
								 HttpSession session) {
		try {
			if (publicUserService.updateNickname(newNickname, id)) {
				session.setAttribute("nickname", newNickname);
				redirectAttributes.addFlashAttribute("success", "Nombre de usuario actualizado");
			} else {
				redirectAttributes.addFlashAttribute("error", "Nombre no valido");
			}
		} catch (KnowyException e) {
			log.error("An error occur while trying to update nickname", e);
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/user-account";
	}

	@PostMapping("/update-email")
	public String updateEmail(@ModelAttribute UserConfigSessionDTO userConfigSessionDTO,
							  RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			privateUserService.updateEmail(
				userConfigSessionDTO.getEmail(),
				userConfigSessionDTO.getNewEmail(),
				userConfigSessionDTO.getCurrentPassword()
			);
			session.setAttribute("email", userConfigSessionDTO.getNewEmail());
			redirectAttributes.addFlashAttribute("successEmail", "Email actualizado");
		} catch (IllegalKnowyPasswordException | KnowyUserEmailUpdateException e) {
			log.error("Error updating email", e);
			redirectAttributes.addFlashAttribute("errorEmail", e.getMessage());;
		}

		return "redirect:/user-account";
	}

	// Delete account
	@GetMapping("/delete-account")
	public String deleteAccountForm(ModelMap interfaceScreen, HttpSession session) {
		interfaceScreen.addAttribute("username", username);
		return "pages/user-management/delete-account";
	}

	//Delete-Account-End (Finally deleting Account)
	@GetMapping("/delete-account-end")
	public String deleteAccountEnd(ModelMap interfaceScreen, HttpSession session) {
		interfaceScreen.addAttribute("username", username);
		return "pages/user-management/delete-account-end";
	}


}
