package com.knowy.server.controller;

import com.knowy.server.controller.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static com.knowy.server.controller.AccessController.SESSION_LOGGED_USER;

@ControllerAdvice
public class ErrorsController {

	@ExceptionHandler(NoResourceFoundException.class)
	public ModelAndView handleNotFound(NoResourceFoundException ex, HttpSession session) {
		ModelAndView mv = new ModelAndView("error/404");
		mv.setStatus(HttpStatus.NOT_FOUND);
		mv.addObject("statusCode", 404);
		mv.addObject("errorMessage", ex.getMessage());
		addSessionUserAttributes(session, mv);
		return mv;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleServerError(Exception ex, HttpSession session) {
		ModelAndView mv = new ModelAndView("error/500");
		mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		mv.addObject("statusCode", 500);
		mv.addObject("errorMessage", ex.getMessage());
		addSessionUserAttributes(session, mv);
		return mv;
	}

	private void addSessionUserAttributes(HttpSession session, ModelAndView mv) {
		SessionUser loggedUser = (SessionUser) session.getAttribute(SESSION_LOGGED_USER);
		if (loggedUser != null) {
			mv.addObject("nickname", loggedUser.nickname());
			mv.addObject("profileImageUrl", loggedUser.profileImageUrl());
		}
	}
}