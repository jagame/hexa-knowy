package com.knowy.server.application.domain;

import java.time.LocalDateTime;

public record UserExercise(User user, Exercise exercise, Integer rate, LocalDateTime nextReview) {
}
