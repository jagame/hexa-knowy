package com.knowy.server.controller;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
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
		PrivateUserEntity privateUser = userService.getCurrentPrivateUser("usuario123@correo.com");
		model.addAttribute("privateUser", privateUser);
		PublicUserEntity publicUser = userService.getCurrentPublicUser(3);
		model.addAttribute("publicUser", publicUser);
		return "pages/user-management/user-account";
	}
	@PostMapping("/update-Nickname")
	public String updatePrivateUsername(@RequestParam String newNickName, @RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(userService.updateNickname(newNickName, id)){
			redirectAttributes.addFlashAttribute("success", "Nombre privado actualizado");
		}else{
			redirectAttributes.addFlashAttribute("error", "Nombre no valido");
		}
		return "redirect:/user-account";
	}

	@PostMapping("/update-email")
	public String updateEmail(@RequestParam String email, @RequestParam String newEmail, @RequestParam String currentPassword, RedirectAttributes redirectAttributes){
		if(userService.updateEmail(email, newEmail, currentPassword)){
			redirectAttributes.addFlashAttribute("successEmail", "Email actualizado");
		}else{
			redirectAttributes.addFlashAttribute("error", "Algo salió mal");
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
