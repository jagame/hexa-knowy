package com.knowy.server.controller;

import com.knowy.server.entity.PrivateUserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {
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

