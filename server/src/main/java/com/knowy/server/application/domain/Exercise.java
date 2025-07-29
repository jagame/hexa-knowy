package com.knowy.server.application.domain;

import java.util.List;

public record Exercise(Integer id, int lessonId, String question, List<Option> options) {
}
