package com.knowy.server.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserConfigDTO {
	private Long id;
	private String username;
	private String privateUsername;
	private String email;
	private String password;
	private List<String> favoriteLanguages;


}
