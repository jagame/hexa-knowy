package com.knowy.server.application.service;


import com.knowy.server.application.port.persistence.QuizRepository;
import com.knowy.server.infrastructure.controller.dto.OptionQuizDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OptionQuizService {
	private final QuizRepository quizRepository;


	public OptionQuizService(QuizRepository quizRepository) {
		this.quizRepository = quizRepository;
	}


	public List<OptionQuizDTO> getOptionsForQuiz(int quizID) {
		return quizRepository.findOptionsByQuizId(quizID);
	}
}
