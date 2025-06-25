package com.knowy.server.controller;

import com.knowy.server.controller.dto.UserConfigSessionDTO;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.service.UserService;
import jakarta.servlet.http.HttpSession;
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
	public String viewUserAccount(Model model, HttpSession session) {
		String email = getCurrentEmail(session);

		PrivateUserEntity privateUser = userService.getCurrentPrivateUser(email);
		model.addAttribute("privateUser", privateUser);
		PublicUserEntity publicUser = userService.getCurrentPublicUser(3);
		model.addAttribute("publicUser", publicUser);

		UserConfigSessionDTO userConfigSessionDTO = new UserConfigSessionDTO();
		userConfigSessionDTO.setEmail(privateUser.getEmail());
		model.addAttribute("userConfigSessionDTO", userConfigSessionDTO);
		return "pages/user-management/user-account";
	}
	@PostMapping("/update-Nickname")
	public String updatePrivateUsername(String newNickname, Integer id, RedirectAttributes redirectAttributes) {
		if(userService.updateNickname(newNickname, id)) {
			redirectAttributes.addFlashAttribute("success", "Nombre privado actualizado");
		}else{
			redirectAttributes.addFlashAttribute("error", "Nombre no valido");
		}
		return "redirect:/user-account";
	}

	@PostMapping("/update-email")
	public String updateEmail(@ModelAttribute UserConfigSessionDTO userConfigSessionDTO,
							  RedirectAttributes redirectAttributes, HttpSession session){
		if(userService.updateEmail(userConfigSessionDTO.getEmail(), userConfigSessionDTO.getNewEmail(), userConfigSessionDTO.getCurrentPassword())){
			session.setAttribute("email", userConfigSessionDTO.getNewEmail());
			redirectAttributes.addFlashAttribute("successEmail", "Email actualizado");
		}else{
			redirectAttributes.addFlashAttribute("errorEmail", "Algo salió mal");
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

	private String getCurrentEmail(HttpSession session){
		String email = (String) session.getAttribute("email");
		if (email == null) {
			email = "usuario123@correo.com";
			session.setAttribute("email", email);
		}
		return email;
	}
}
