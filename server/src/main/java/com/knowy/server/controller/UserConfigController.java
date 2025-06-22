package com.knowy.server.controller;

import com.knowy.server.dto.UserProfileDTO;
import com.knowy.server.entities.UserEntity;
import com.knowy.server.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
	private final UserService userService;

	public UserConfigController(UserService userService) {
		this.userService = userService;
	}

	//Update User-profile
	@PostMapping("/update-user-profile")
	public String updateUserProfile(@ModelAttribute("profileDto") UserProfileDTO userProfileDTO,
									Model model) {

		//check if username is already taken
		if(userService.takenUserName(userProfileDTO.getUsername())) {
			model.addAttribute("error", "Ese nombre de usuario ya existe");
			return "pages/user-management/user-profile";
		}

		//check if username contains banned words
		if (userService.inappropriateName(userProfileDTO.getUsername())) {
			model.addAttribute("error", "El nombre de usuario contiene palabras inapropiadas");
			return "pages/user-management/user-profile";

		} else {
			userService.updateProfile(userProfileDTO.getUsername(), userProfileDTO.getProfilePicture());
			userService.updateFavLanguages(userProfileDTO.getLanguages());

			model.addAttribute("success", "Perfil actualizado correctamente");
			model.addAttribute("username", userService.getUserTest().getUsername());
			model.addAttribute("profilePicture", userService.getUserTest().getProfilePicture());
			model.addAttribute("languages", userService.getUserTest().getFavouriteLanguages());

		}
		return "pages/user-management/user-profile";
	}
}
