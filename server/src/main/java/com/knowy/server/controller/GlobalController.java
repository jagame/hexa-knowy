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
			// Recuperar el objeto de usuario almacenado en la sesión
			PrivateUserEntity loggedUser = (PrivateUserEntity) session.getAttribute("loggedUser");
			// Verificar que exista un usuario en sesión y que tenga datos públicos vinculados
			if (loggedUser != null && loggedUser.getPublicUserEntity() != null) {
				// Obtener el apodo (nickname) desde la entidad de datos públicos
				String nickname = loggedUser.getPublicUserEntity().getNickname();
				model.addAttribute("username", nickname);
				// TODO: Obtener y añadir la URL de la imagen de perfil del usuario
			}
		}
}

