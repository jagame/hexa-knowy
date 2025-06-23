package com.knowy.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserConfiguration {
	@Id
	private Integer id;
	private String username;
	private String privateUsername;
	private String email;
	private String password;
	private List<String> favoriteLanguages;
}
