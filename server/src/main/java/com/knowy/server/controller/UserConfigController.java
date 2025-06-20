package com.knowy.server.controller;

import com.knowy.server.controller.dto.UserConfigDTO;
import com.knowy.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserConfigController {

	private UserService userService;

	public UserConfigController(UserService userService) {
		this.userService = userService;
	}


	String username = "usuario123";
	//User-Profile
	@GetMapping("/user-profile")
	public String viewUserProfile(Model model) {
		model.addAttribute("username", username);
		return "pages/user-management/user-profile";
	}

	//User-Account
	@GetMapping("/user-account")
	public String viewUserAccount(Model model) {
		UserConfigDTO user = userService.getCurrentUser();
		model.addAttribute("user", user);
		return "pages/user-management/user-account";
	}
	@PostMapping("/update-privateUsername")
	public String updatePrivateUsername(@RequestParam String newPrivateUsername, RedirectAttributes redirectAttributes) {
		if (userService.validatePrivateUsername(newPrivateUsername)){
			userService.setPrivateUsername(newPrivateUsername);
			redirectAttributes.addFlashAttribute("success", "Nombre privado actualizado");
		}else{
			redirectAttributes.addFlashAttribute("error", "Nombre no valido");
		}
		return "redirect:/user-account";
	}

	@PostMapping("/update-email")
	public String updateEmail(@RequestParam String newEmail, @RequestParam String currentPassword, RedirectAttributes redirectAttributes){
		if(userService.validateCurrentPassword(currentPassword)&& userService.validateEqualEmail(newEmail)){
			userService.setEmail(newEmail);
			redirectAttributes.addFlashAttribute("successEmail", "Email actualizado");
		}else if(userService.validateCurrentPassword(currentPassword)&& (!userService.validateEqualEmail(newEmail))){
			redirectAttributes.addFlashAttribute("errorEmail", "Email no valido");
		}else{
			redirectAttributes.addFlashAttribute("errorEmail", "Contraseña incorrecta");
		}
		return "redirect:/user-account";
	}

	// Delete account
	@GetMapping("/delete-account")
	public String deleteAccountForm(ModelMap interfaceScreen) {
		interfaceScreen.addAttribute("username", username);
		return "pages/user-management/delete-account";
	}

	//Delete-Account-End (Finally deleting Account)
	@GetMapping ("/delete-account-end")
	public String deleteAccountEnd(ModelMap interfaceScreen) {
		interfaceScreen.addAttribute("username", username);
		return "pages/user-management/delete-account-end";
	}
}
