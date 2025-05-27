package com.knowy.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserConfigController {
	//User-Profile
	@GetMapping("/user-profile")
	public String viewUserProfile(ModelMap interfaceScreen) {
		interfaceScreen.addAttribute("username", "usuario123");
		return "pages/user-management/user-profile";
	}

	//User-Account
	@GetMapping("/user-account")
	public String viewUserAccount(ModelMap interfaceScreen) {
		interfaceScreen.addAttribute("username", "usuario123");
		interfaceScreen.addAttribute("email", "usuario123@correo.com");
		return "pages/user-management/user-account";
	}

	// Delete account
	@GetMapping("/delete-account")
	public String deleteAccountForm(ModelMap interfaceScreen) {
		return "pages/user-management/delete-account";
	}

	//Delete-Account-End (Finally deleting Account)
	@GetMapping ("/delete-account-end")
	public String deleteAccountEnd(ModelMap interfaceScreen) {
		return "pages/user-management/delete-account-end";
	}
}
