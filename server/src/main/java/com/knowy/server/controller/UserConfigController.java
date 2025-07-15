package com.knowy.server.controller;

import com.knowy.server.controller.dto.UserConfigSessionDTO;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.JpaPrivateUserRepository;
import com.knowy.server.repository.JpaPublicUserRepository;
import com.knowy.server.repository.PrivateUserRepository;
import com.knowy.server.service.AccessService;
import com.knowy.server.service.UserService;
import com.knowy.server.util.PasswordCheker;
import com.knowy.server.util.exception.JwtKnowyException;
import com.knowy.server.util.exception.MailDispatchException;
import com.knowy.server.util.exception.WrongPasswordException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.jdbc.support.JdbcAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Controller
public class UserConfigController {

	private final UserService userService;
	private final AccessService accessService;
	private final JpaPrivateUserRepository jpaPrivateUserRepository;
	private final PasswordCheker passwordCheker;
	private final JdbcAccessor jdbcAccessor;
	private final JpaPublicUserRepository jpaPublicUserRepository;

	public UserConfigController(UserService userService, AccessService accessService, JpaPrivateUserRepository jpaPrivateUserRepository, PasswordCheker passwordCheker, JdbcAccessor jdbcAccessor, JpaPublicUserRepository jpaPublicUserRepository) {
		this.userService = userService;
		this.accessService = accessService;
		this.jpaPrivateUserRepository = jpaPrivateUserRepository;
		this.passwordCheker = passwordCheker;
		this.jdbcAccessor = jdbcAccessor;
		this.jpaPublicUserRepository = jpaPublicUserRepository;
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

	private String getCurrentEmail(HttpSession session){
		String email = (String) session.getAttribute("email");
		if (email == null) {
			email = "usuario123@correo.com";
			session.setAttribute("email", email);
			// FixMe: Changing the way we get email in the future
		}
		return email;
	}
	//Method available for use on the other pages of UserConfig
	private String getCurrentNickname(HttpSession session){
		String nickname = (String) session.getAttribute("nickname");
		if(nickname == null) {
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
		if(userService.updateNickname(newNickname, id)) {
			session.setAttribute("nickname", newNickname);
			redirectAttributes.addFlashAttribute("success", "Nombre de usuario actualizado");
		}else{
			redirectAttributes.addFlashAttribute("error", "Nombre no valido");
		}
		return "redirect:/user-account";
	}

	@PostMapping("/update-email")
	public String updateEmail(@ModelAttribute UserConfigSessionDTO userConfigSessionDTO,
							  RedirectAttributes redirectAttributes, HttpSession session){
		if(userService.updateEmail(userConfigSessionDTO.getEmail(),
			userConfigSessionDTO.getNewEmail(),
			userConfigSessionDTO.getCurrentPassword())){
			session.setAttribute("email", userConfigSessionDTO.getNewEmail());
			redirectAttributes.addFlashAttribute("successEmail", "Email actualizado");
		}else{
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
	@GetMapping ("/delete-account-end")
	public String deleteAccountEnd(ModelMap interfaceScreen, HttpSession session) {
		interfaceScreen.addAttribute("username", username);
		return "pages/user-management/delete-account-end";
	}

	@PostMapping("/delete-account-end")
	public String deleteAccount (@RequestParam String deletePassword,
								 @RequestParam String confirmPassword,
								 RedirectAttributes redirectAttributes,
								 HttpSession session,
								 ModelMap interfaceScreen,
								 HttpServletRequest request) throws WrongPasswordException, JwtKnowyException, MailDispatchException {

		String email = getCurrentEmail(session);

		try {
			accessService.deactivateUserAccount(email, deletePassword, confirmPassword, request);
			redirectAttributes.addFlashAttribute("success", "Tu cuenta ha sido desactivada correctamente. Dispones de 30 días para recuperarla.");
			return "redirect:/";

		} catch (WrongPasswordException e) {
			interfaceScreen.addAttribute("error", "La contraseña es incorrecta o no coincide");
			return "/pages/user-management/delete-account-end";
		}
	}

	@GetMapping("/reactivate-account")
	public String reactivateAccount(@RequestParam("token") String token, RedirectAttributes redirectAttributes) throws JwtKnowyException {

			try {
				accessService.reactivateUserAccount(token);
				redirectAttributes.addFlashAttribute("success", "Tu cuenta ha sido reactivada correctamente.");
				return "redirect:/account-reactivation";

			} catch (JwtKnowyException e) {
				redirectAttributes.addFlashAttribute("error", "El token ha expirado o no es válido");
				return "redirect:/error";
			}
	}

	@GetMapping("account-reactivation")
	public String accountReactivation() {
		return "pages/user-management/account-reactivation";
	}

}
