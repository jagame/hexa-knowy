package com.knowy.server.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ErrorsController {

	@ExceptionHandler(NoResourceFoundException.class)
	public ModelAndView handleNotFound(NoResourceFoundException ex, HttpSession session) {
		ModelAndView mv = new ModelAndView("error/404");
		mv.setStatus(HttpStatus.NOT_FOUND);
		mv.addObject("statusCode", 404);
		mv.addObject("errorMessage", ex.getMessage());
		return mv;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleServerError(Exception ex, HttpSession session) {
		ModelAndView mv = new ModelAndView("error/500");
		mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		mv.addObject("statusCode", 500);
		mv.addObject("errorMessage", ex.getMessage());
		return mv;
	}
}