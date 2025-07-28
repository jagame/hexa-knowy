package com.knowy.server.application.domain;

import java.time.LocalDateTime;

public class UserExercise {
	private int userId;
	private int exerciseId;
	private Integer rate;
	private LocalDateTime nextReview;

	public int userId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int exerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public Integer rate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public LocalDateTime nextReview() {
		return nextReview;
	}

	public void setNextReview(LocalDateTime nextReview) {
		this.nextReview = nextReview;
	}
}
