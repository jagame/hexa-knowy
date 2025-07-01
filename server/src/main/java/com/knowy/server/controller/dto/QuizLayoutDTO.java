package com.knowy.server.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizLayoutDTO {
	private String courseName;
	private int lessonNumber;
	private int quizCardNumber;
	private int cardStreak;
	private int correctPercentage;
}
