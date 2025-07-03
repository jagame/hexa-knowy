package com.knowy.server.infrastructure.controller;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.error.*;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.PublicUser;
import com.knowy.server.application.service.PrivateUserService;
import com.knowy.server.application.service.PublicUserService;
import com.knowy.server.application.service.usecase.update.email.KnowyUserEmailUpdateException;
import com.knowy.server.infrastructure.controller.dto.SessionUser;
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
	public String viewUserProfile() {
		return "pages/user-management/user-profile";
	}

	//User-Account
	@GetMapping("/user-account")
	public String viewUserAccount() {
		return "pages/user-management/user-account";
	}

	@PostMapping("/update-Nickname")
	public String updateNickname(String newNickname, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute(AccessController.SESSION_LOGGED_USER);
			if (publicUserService.updateNickname(newNickname, sessionUser.id())) {
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
			SessionUser sessionUser = (SessionUser) session.getAttribute(AccessController.SESSION_LOGGED_USER);
			privateUserService.updateEmail(
				new Email(sessionUser.email()),
				userConfigSessionDTO.getNewEmail(),
				userConfigSessionDTO.getCurrentPassword()
			);
			session.setAttribute("email", userConfigSessionDTO.getNewEmail());
			redirectAttributes.addFlashAttribute("successEmail", "Email actualizado");
		} catch (IllegalKnowyPasswordException | KnowyUserEmailUpdateException | IllegalKnowyEmailException e) {
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
