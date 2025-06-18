package com.knowy.server.controller.model;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
	private String questionNumber;
	private String questionText;
	private String imgPath;

}
