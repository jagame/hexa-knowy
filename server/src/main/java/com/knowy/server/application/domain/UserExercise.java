package com.knowy.server.application.domain;

import java.time.LocalDateTime;

public record UserExercise(int userId, int exerciseId, Integer rate, LocalDateTime nextReview) {
}
