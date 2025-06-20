package com.knowy.server.controller;

import com.knowy.server.services.UserSrvc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserConfigController {
	String username = "usuario123";
	//User-Profile
	@GetMapping("/user-profile")
	public String viewUserProfile(ModelMap interfaceScreen) {
		interfaceScreen.addAttribute("username", username);
		return "pages/user-management/user-profile";
	}

	//User-Account
	@GetMapping("/user-account")
	public String viewUserAccount(ModelMap interfaceScreen) {
		interfaceScreen.addAttribute("username", username);
		interfaceScreen.addAttribute("email", "usuario123@correo.com");
		return "pages/user-management/user-account";
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

	//Service to validate username
	private final UserSrvc userSrvc;

	public UserConfigController(UserSrvc userSrvc) {
		this.userSrvc = userSrvc;
	}

	//Update User-profile
	@PostMapping("/update-user-profile")
	public String updateUserProfile(@RequestParam("username") String newUserName,
									@RequestParam(value= "profilePicture", required = false) String newProfilePicture,
									@RequestParam(value= "languages", required = false) List<String> languages,
									Model model) {

		//check if username is already taken
		if(userSrvc.takenUserName(newUserName)) {
			model.addAttribute("error", "Ese nombre de usuario ya existe");
			return "pages/user-management/user-profile";
		}

		//check if username contains banned words
		if (userSrvc.inappropriateName(newUserName)) {
			model.addAttribute("error", "El nombre de usuario contiene palabras inapropiadas");
			return "pages/user-management/user-profile";

		} else {
			userSrvc.updateProfile(newUserName, newProfilePicture);
			userSrvc.updateFavLanguages(languages);
			model.addAttribute("success", "Perfil actualizado correctamente");
			model.addAttribute("username", userSrvc.getUserTest().getUsername());
			model.addAttribute("profilePicture", userSrvc.getUserTest().getProfilePicture());
			model.addAttribute("languages", userSrvc.getUserTest().getFavouriteLanguages());

		}
		return "pages/user-management/user-profile";
	}
}
