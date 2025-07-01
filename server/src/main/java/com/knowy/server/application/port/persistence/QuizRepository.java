package com.knowy.server.application.port.persistence;

import com.knowy.server.infrastructure.controller.dto.OptionQuizDTO;

import java.util.List;

public interface QuizRepository {

	List<OptionQuizDTO> findOptionsByQuizId(int quizID);

}
