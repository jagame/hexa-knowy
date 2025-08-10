package com.knowy.server.domain;

import java.time.LocalDateTime;

public record UserExercise(User user, Exercise exercise, Integer rate, LocalDateTime nextReview) {

	public UserExercise(User user, Exercise exercise, Integer rate, LocalDateTime nextReview) {
		this.user = user;
		this.exercise = exercise;
		this.rate = normalizeRate(rate);
		this.nextReview = nextReview;
	}

	private static Integer normalizeRate(Integer rate) {
		return Math.clamp(rate, 0, 100);
	}
}