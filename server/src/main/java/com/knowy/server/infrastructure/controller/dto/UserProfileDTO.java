package com.knowy.server.infrastructure.controller.dto;

import lombok.*;

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
