package com.knowy.server.application.domain;

import com.knowy.server.infrastructure.adapters.repository.entity.LessonEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.OptionEntity;

import java.util.List;

public class Exercise {
	private Integer id;
	private LessonEntity lesson;
	private String question;
	private List<OptionEntity> options;

	public Integer id() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LessonEntity lesson() {
		return lesson;
	}

	public void setLesson(LessonEntity lesson) {
		this.lesson = lesson;
	}

	public String question() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<OptionEntity> options() {
		return options;
	}

	public void setOptions(List<OptionEntity> options) {
		this.options = options;
	}
}
