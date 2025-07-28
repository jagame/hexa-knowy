package com.knowy.server.infrastructure.controller.dto;

public class ToastDto {
	private String status;
	private String message;
	private ToastType type;

	public enum ToastType {
		SUCCESS, ERROR, WARNING, INFO
	}

	public ToastDto() {}
	public ToastDto(String status, String message, ToastType type) {
		this.status = status; this.message = message; this.type = type;
	}

	public String getStatus()  { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }

	public ToastType getType() { return type; }
	public void setType(ToastType type) { this.type = type; }
}