package com.knowy.server.controller.dto;

import com.knowy.server.entity.LanguageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {


	private String username;
//	private String profilePicture;
	private Set<LanguageEntity> languages;
}
