package com.knowy.server.controller.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserProfileDTO {

	private String nickname;
	private Integer profilePictureId;
	private String[] languages;
}
