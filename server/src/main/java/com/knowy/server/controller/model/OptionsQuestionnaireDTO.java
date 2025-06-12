package com.knowy.server.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionsQuestionnaireDTO {
	private String letter;
	private String text;
	private boolean correct;

}
