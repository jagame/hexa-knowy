package com.knowy.server.application.service;


import com.knowy.server.infrastructure.adapter.repository.OptionsQuizRepositoryDummy;
import com.knowy.server.infrastructure.controller.dto.OptionQuizDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OptionQuizService {
	private final OptionsQuizRepositoryDummy optionsQuizRepositoryDummy;


	public OptionQuizService(OptionsQuizRepositoryDummy optionsQuizRepositoryDummy) {
		this.optionsQuizRepositoryDummy = optionsQuizRepositoryDummy;
	}


	public List<OptionQuizDTO> getOptionsForQuiz(int quizID) {
		return optionsQuizRepositoryDummy.findOptionsByQuizId(quizID);
	}
}
