package com.knowy.server.controller.dto;

import java.util.List;

public record ExerciseDto(
	int courseId,
	int lessonId,
	int exerciseId,
	//QUIZLAYOUTDTO
	String courseName,
	int cardStreak,
	int correctPercentage,
	//QUESTIONDTO
	String questionText,
	String imgPath,
	List<OptionsDto> options
) {
}
