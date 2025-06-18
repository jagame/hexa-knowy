package com.knowy.server.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserConfigDTO {
	private Long id;
	private String username;
	private String email;
	private String password;
	private List<String> favoriteLanguages;


	public UserConfigDTO() {
		this.favoriteLanguages = new ArrayList<>();
	}

	public UserConfigDTO(Long id, String username, String email, String password) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.favoriteLanguages = new ArrayList<>();
	}
}
