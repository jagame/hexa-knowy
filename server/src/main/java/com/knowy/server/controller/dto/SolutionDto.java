package com.knowy.server.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolutionDto {
	private String cardTitle;
	private String question;
	private String answer;
}
