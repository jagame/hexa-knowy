package com.knowy.server.controller;

import com.knowy.server.controller.dto.SessionUser;
import com.knowy.server.controller.dto.UserConfigSessionDTO;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.controller.dto.UserProfileDTO;
import com.knowy.server.repository.JpaLanguageRepository;
import com.knowy.server.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.knowy.server.controller.AccessController.SESSION_LOGGED_USER;

@Slf4j
@Controller
public class UserConfigController {

	private final UserService userService;

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
		Optional<PrivateUserEntity> privateUser = userService.findPrivateUserByEmail(email);
		// FixMe: Changing the way we get UserById in the future
		Optional<PublicUserEntity> publicUser = userService.findPublicUserById(3);

		if (privateUser.isEmpty() || publicUser.isEmpty()) {
			// Manejo de error: usuario no encontrado
			model.addAttribute("error", "User information could not be uploaded.");
			return "pages/user-management/user-account";
		}

		model.addAttribute("privateUser", privateUser.get());
		model.addAttribute("publicUser", publicUser.get());
		session.setAttribute("nickName", publicUser.get().getNickname());

		UserConfigSessionDTO userConfigSessionDTO = new UserConfigSessionDTO();
		userConfigSessionDTO.setEmail(privateUser.get().getEmail());
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
	private String getCurrentNickname(HttpSession session) {
		String nickname = (String) session.getAttribute("nickname");
		if (nickname == null) {
			// FixMe: Changing the way we get UserById in the future
			Optional<PublicUserEntity> publicUser = userService.findPublicUserById(3);
			publicUser.ifPresent(publicUserEntity -> session.setAttribute("nickname", publicUserEntity.getNickname()));
		}
		return nickname;
	}

	@PostMapping("/update-Nickname")
	public String updateNickname(String newNickname, Integer id,
								 RedirectAttributes redirectAttributes,
								 HttpSession session) {
		if (userService.updateNickname(newNickname, id)) {
			session.setAttribute("nickname", newNickname);
			redirectAttributes.addFlashAttribute("success", "Nombre de usuario actualizado");
		} else {
			redirectAttributes.addFlashAttribute("error", "Nombre no valido");
		}
		return "redirect:/user-account";
	}

	@PostMapping("/update-email")
	public String updateEmail(@ModelAttribute UserConfigSessionDTO userConfigSessionDTO,
							  RedirectAttributes redirectAttributes, HttpSession session) {
		if (userService.updateEmail(userConfigSessionDTO.getEmail(),
			userConfigSessionDTO.getNewEmail(),
			userConfigSessionDTO.getCurrentPassword())) {
			session.setAttribute("email", userConfigSessionDTO.getNewEmail());
			redirectAttributes.addFlashAttribute("successEmail", "Email actualizado");
		} else {
			redirectAttributes.addFlashAttribute("errorEmail", "Algo salió mal");
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


	//Update User-profile
	@PostMapping("/update-user-profile")
	public String updateUserProfile(@RequestParam("languages") Set<String> languages, @ModelAttribute("profileDto") UserProfileDTO userProfileDTO, Model model, HttpSession session) {
		SessionUser loggedUser = (SessionUser) session.getAttribute(SESSION_LOGGED_USER);
		userProfileDTO.setLanguages(languages);
		log.warn(userProfileDTO.toString());

		//check if username is already taken
		if (userService.isTakenUsername(userProfileDTO.getNickname())) {
			model.addAttribute("error", "Ese nombre de usuario ya existe");
			return "pages/user-management/user-profile";
		}

		//check if username contains banned words
		if (userService.isNicknameBanned(userProfileDTO.getNickname())) {
			model.addAttribute("error", "El nombre de usuario contiene palabras inapropiadas");
			return "pages/user-management/user-profile";

		}
		userService.updateUserProfile(loggedUser.id(), userProfileDTO.getNickname(), userProfileDTO.getLanguages());

		model.addAttribute("success", "Perfil actualizado correctamente");
		model.addAttribute("username", userProfileDTO.getNickname());
//			model.addAttribute("profilePicture", userProfileDTO.getProfilePicture());
		model.addAttribute("languages", userProfileDTO.getLanguages());
		return "pages/user-management/user-profile";

	}
}
