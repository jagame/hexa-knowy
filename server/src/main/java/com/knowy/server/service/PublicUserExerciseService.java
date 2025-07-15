package com.knowy.server.service;

import com.knowy.server.entity.PublicUserExerciseEntity;
import com.knowy.server.repository.ports.PublicUserExerciseRepository;
import com.knowy.server.service.model.ExerciseDifficult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Service
public class PublicUserExerciseService {

	private final PublicUserExerciseRepository publicUserExerciseRepository;

	public PublicUserExerciseService(PublicUserExerciseRepository publicUserExerciseRepository) {
		this.publicUserExerciseRepository = publicUserExerciseRepository;
	}

	public Optional<PublicUserExerciseEntity> findPullByPublicUser(int publicUserId) {
		return publicUserExerciseRepository.findAllByPublicUserId(publicUserId)
			.stream()
			.min(
				Comparator.comparing(PublicUserExerciseEntity::getRate)
					.thenComparing(PublicUserExerciseEntity::getNextReview)
			);
	}

	public void difficultSelect(ExerciseDifficult exerciseDifficult, PublicUserExerciseEntity publicUserExerciseEntity) {
		switch (exerciseDifficult) {
			case EASY -> easySelect(publicUserExerciseEntity);
			case MEDIUM -> mediumSelect(publicUserExerciseEntity);
			case HARD -> hardSelect(publicUserExerciseEntity);
			case FAIL -> failSelect(publicUserExerciseEntity);
		}
	}

	private void easySelect(PublicUserExerciseEntity publicUserExerciseEntity) {
		publicUserExerciseEntity.setRate(publicUserExerciseEntity.getRate() + 45);

		if (publicUserExerciseEntity.getRate() >= 90) {
			publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusDays(1));
		} else {
			publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusMinutes(15));
		}
	}

	private void mediumSelect(PublicUserExerciseEntity publicUserExerciseEntity) {
		publicUserExerciseEntity.setRate(publicUserExerciseEntity.getRate() + 20);

		if (publicUserExerciseEntity.getRate() >= 90) {
			publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusDays(1));
		} else {
			publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusMinutes(10));
		}
	}

	private void hardSelect(PublicUserExerciseEntity publicUserExerciseEntity) {
		publicUserExerciseEntity.setRate(publicUserExerciseEntity.getRate() - 15);
		publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusMinutes(5));
	}

	private void failSelect(PublicUserExerciseEntity publicUserExerciseEntity) {
		publicUserExerciseEntity.setRate(publicUserExerciseEntity.getRate() + 30);
		publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusMinutes(1));
	}

}
