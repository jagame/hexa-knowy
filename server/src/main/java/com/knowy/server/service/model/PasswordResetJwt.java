package com.knowy.server.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetJwt {
	private int userId;
	private String email;
	private TokenTypeJwt type;
}
