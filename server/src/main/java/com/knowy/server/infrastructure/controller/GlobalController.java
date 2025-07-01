package com.knowy.server.infrastructure.controller;

import com.knowy.server.controller.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import static com.knowy.server.infrastructure.controller.AccessController.SESSION_LOGGED_USER;

@ControllerAdvice
public class GlobalController {

    @ModelAttribute
    public void addUserDataToModel(HttpSession session, Model model) {

        SessionUser loggedUser = (SessionUser) session.getAttribute(SESSION_LOGGED_USER);

        if (loggedUser != null && loggedUser.nickname() != null) {
            model.addAttribute("nickname", loggedUser.nickname());
            model.addAttribute("profileImageUrl", loggedUser.profileImageUrl());
        }
    }
}