package com.knowy.server.infrastructure.adapter.repository;


import com.knowy.server.application.port.persistence.QuizRepository;
import com.knowy.server.infrastructure.controller.dto.OptionQuizDTO;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;


@Repository
public class OptionsQuizRepositoryDummy implements QuizRepository {
	@Override
	public List<OptionQuizDTO> findOptionsByQuizId(int quizID) {
		//Simulating the data, we will connect the DDBB in the near future.
		return Arrays.asList(
			new OptionQuizDTO(1, 2, 3, "A.", "Esta respuesta es errónea.", false),
			new OptionQuizDTO(1, 2, 3, "B.", "Esta respuesta es muy buena gente pero falsa.", false),
			new OptionQuizDTO(1, 2, 3, "C.", "Esta respuesta mola cantidubi.", true),
			new OptionQuizDTO(1, 2, 3, "D.", "Esta respuesta te traerá dolores de cabeza.", false)
		);
	}
}
