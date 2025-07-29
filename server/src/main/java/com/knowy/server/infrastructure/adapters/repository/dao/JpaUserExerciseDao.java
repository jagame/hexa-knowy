package com.knowy.server.infrastructure.adapters.repository.dao;

import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserExerciseEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserExerciseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserExerciseDao extends JpaRepository<PublicUserExerciseEntity, PublicUserExerciseId> {

	@NonNull
	<S extends PublicUserExerciseEntity> S save(@NonNull S publicUserExerciseEntity);

	@NonNull
	Optional<PublicUserExerciseEntity> findById(@NonNull PublicUserExerciseId id);

	@NonNull
	List<PublicUserExerciseEntity> findAll();

	@Query(value = """
		SELECT
		    pl.id_public_user AS id_public_user,
		    ex.id AS id_exercise,
		    COALESCE(pex.next_review, NOW()) AS next_review,
		    COALESCE(pex.rate, 0) AS rate
		FROM public_user_lesson pl
		    INNER JOIN lesson l
		        ON pl.id_lesson = l.id
		    INNER JOIN course c
		        ON l.id_course = c.id
		    INNER JOIN exercise ex
		        ON l.id = ex.id_lesson
		    FULL JOIN public_user_exercise pex
		        ON ex.id = pex.id_exercise
		WHERE
		    pl.id_public_user = :userId AND
		    l.id = :lessonId AND
		    pl.status != 'pending'
		ORDER BY
		    pex.next_review NULLS FIRST,
		    pex.rate NULLS FIRST,
		    RANDOM()
		LIMIT(1)
		""", nativeQuery = true)
	Optional<PublicUserExerciseEntity> findNextExerciseByLessonId(int userId, int lessonId);

	@Query(value = """
		SELECT
		    pl.id_public_user AS id_public_user,
		    ex.id AS id_exercise,
		    COALESCE(pex.next_review, NOW()) AS next_review,
		    COALESCE(pex.rate, 0) AS rate
		FROM public_user_lesson pl
		    INNER JOIN lesson l
		        ON pl.id_lesson = l.id
		    INNER JOIN course c
		        ON l.id_course = c.id
		    INNER JOIN exercise ex
		        ON l.id = ex.id_lesson
		    FULL JOIN public_user_exercise pex
		        ON ex.id = pex.id_exercise
		WHERE
		    pl.id_public_user = :userId AND
		    pl.status != 'pending'
		ORDER BY
		    pex.next_review NULLS FIRST,
		    pex.rate NULLS FIRST,
		    RANDOM()
		LIMIT(1)
		""", nativeQuery = true)
	Optional<PublicUserExerciseEntity> findNextExerciseByUserId(int userId);

	@Query(value = """
		SELECT
		    COALESCE(AVG(public_user_exercise.rate), 0) AS average_rate
		FROM lesson l
		    INNER JOIN exercise
		        ON l.id = exercise.id_lesson
		    LEFT JOIN public_user_exercise
		        ON exercise.id = public_user_exercise.id_exercise
		WHERE
		    l.id = :lessonId
		GROUP BY
		    l.id
		""", nativeQuery = true)
	Optional<Double> findAverageRateByLessonId(int lessonId);
}
