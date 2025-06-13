package com.knowy.server.service;

import com.knowy.server.controller.model.OptionQuizDTO;
import com.knowy.server.repository.OptionsQuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionQuizService {
	private final OptionsQuizRepository optionsQuizRepository;

	public OptionQuizService(OptionsQuizRepository optionsQuizRepository) {
		this.optionsQuizRepository = optionsQuizRepository;
	}

	public List<OptionQuizDTO> getOptionsForQuiz(int quizID){
		return optionsQuizRepository.findOptionsByQuizId(quizID);
	}
}
