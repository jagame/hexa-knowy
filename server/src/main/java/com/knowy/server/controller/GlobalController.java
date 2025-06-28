package com.knowy.server.controller;

import com.knowy.server.entity.PrivateUserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

	/**
	 * FixMe - Dejar de exponer el usuario privado entero y como mucho exponer el públic pero sin id ni referencias a
	 * FixMe - la tabla de private_user
	 */
	@ModelAttribute
	public void addUserDataToModel(HttpSession session, Model model) {

		PrivateUserEntity loggedUser = (PrivateUserEntity) session.getAttribute("loggedUser");

		if (loggedUser != null && loggedUser.getPublicUserEntity() != null) {
			String nickname = loggedUser.getPublicUserEntity().getNickname();
			model.addAttribute("username", nickname);

			String avatarUrl = loggedUser.getPublicUserEntity().getProfileImage().getUrl();
			model.addAttribute("avatar", avatarUrl);
		}
	}
}