package com.knowy.server.controller.dto;

public class ToastDto {
	private String status;
	private String message;
	private String color;
	enum type {
		SUCCESS, ERROR, WARNING, INFO
	}
}
