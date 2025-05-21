package com.knowy.server.controller;

import com.knowy.server.controller.model.UserConfigDTO;
import com.knowy.server.service.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserConfigController {

	private final UserConfigService userService;

	@Autowired
	public UserConfigController(UserConfigService userService) {
		this.userService = userService;
	}

	// User profile
	@GetMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("user", userService.getCurrentUser());
		return "pages/profile";
	}

	@PostMapping("/profile")
	public String updateProfile(@ModelAttribute UserConfigDTO user, @RequestParam(required = false) MultipartFile profilePic,
								RedirectAttributes redirectAttributes) {
		// Logic for handling the uploaded image
		return "redirect:/pages/user-management/profile";
	}
	// Account
	@GetMapping("/account")
	public String account(Model model) {
		model.addAttribute("user", userService.getCurrentUser());
		return "pages/account";
	}

	@PostMapping("/account")
	public String updateAccount(@ModelAttribute UserConfigDTO user, @RequestParam(required = false) String newPassword,
								@RequestParam(required = false) String confirmPassword, RedirectAttributes redirectAttributes) {

		return "redirect:/pages/account";
	}
	// Delete account
	@GetMapping("/delete-account")
	public String deleteAccountForm(Model model) {
		model.addAttribute("user", userService.getCurrentUser());
		return "pages/delete-account";
	}

	@GetMapping("/delete-account-continuation")
	public String deleteAccountFormContinuation(Model model) {
		model.addAttribute("user", userService.getCurrentUser());
		return "pages/user-management/delete-account-continuation";
	}

	@PostMapping("/delete-account")
	public String deleteAccount(@RequestParam String password, @RequestParam String confirmPassword,
								RedirectAttributes redirectAttributes) {

		if (!password.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
			return "redirect:/pages/delete-account";
		}

		UserConfigDTO user = userService.getCurrentUser();
		boolean deleted = userService.deleteAccount(user.getId(), password);

		if (deleted) {
			return "redirect:/logout";
		} else {
			redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la cuenta");
			return "redirect:/pages/delete-account";
		}
	}

	//Logout
	@GetMapping("/logout-confirmation")
	public String logoutConfirmation(Model model) {
		model.addAttribute("user", userService.getCurrentUser());
		return "pages/logout-confirmation";
	}
}
