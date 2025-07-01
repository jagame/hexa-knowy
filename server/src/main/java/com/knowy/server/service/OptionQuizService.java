package com.knowy.server.service;


import com.knowy.server.controller.dto.OptionQuizDTO;
import com.knowy.server.repository.OptionsQuizRepositoryDummy;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class OptionQuizService {
    private final OptionsQuizRepositoryDummy optionsQuizRepositoryDummy;


    public OptionQuizService(OptionsQuizRepositoryDummy optionsQuizRepositoryDummy) {
        this.optionsQuizRepositoryDummy = optionsQuizRepositoryDummy;
    }


    public List<OptionQuizDTO> getOptionsForQuiz(int quizID){
        return optionsQuizRepositoryDummy.findOptionsByQuizId(quizID);
    }
}
