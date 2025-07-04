package com.knowy.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ErrorsController {

	@ExceptionHandler(NoResourceFoundException.class)
	public ModelAndView handleNoHandlerFoundException(NoResourceFoundException ex) {
		ModelAndView mv = new ModelAndView();
		mv.setStatus(HttpStatus.NOT_FOUND);
		mv.setViewName("error/404");
		mv.getModelMap().addAttribute("errorMessage", ex.getMessage());
		return mv;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllExceptions(Exception ex) {
		ModelAndView mv = new ModelAndView();
		mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		mv.setViewName("error/500");
		mv.getModelMap().addAttribute("errorMessage", ex.getMessage());
		return mv;
	}
}
