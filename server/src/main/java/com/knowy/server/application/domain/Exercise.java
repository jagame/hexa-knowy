package com.knowy.server.application.domain;

import com.knowy.server.infrastructure.adapters.repository.entity.LessonEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.OptionEntity;

import java.util.List;

public record Exercise(Integer id, LessonEntity lesson, String question, List<OptionEntity> options) {
}
