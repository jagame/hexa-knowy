package com.knowy.server.service;


import com.knowy.server.entity.ExerciseEntity;
import com.knowy.server.repository.ports.ExerciseRepository;
import com.knowy.server.util.exception.ExerciseNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ExerciseService {
	private final ExerciseRepository exerciseRepository;

	public ExerciseService(ExerciseRepository exerciseRepository) {
		this.exerciseRepository = exerciseRepository;
	}

	public ExerciseEntity findById(int id) throws ExerciseNotFoundException {
		return exerciseRepository.findById(id)
			.orElseThrow(() -> new ExerciseNotFoundException("Exercise with id " + id + " not found"));
	}
}
