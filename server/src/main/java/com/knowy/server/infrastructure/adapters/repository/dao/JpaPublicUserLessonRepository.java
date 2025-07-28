package com.knowy.server.infrastructure.adapters.repository.dao;

import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserLessonEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserLessonIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaPublicUserLessonRepository extends JpaRepository<PublicUserLessonEntity, PublicUserLessonIdEntity> {

	boolean existsByUserIdAndLessonId(Integer userId, Integer lessonId);

	@Query("""
		SELECT DISTINCT l.course.id
		FROM PublicUserLessonEntity pul
		JOIN LessonEntity l ON pul.lessonId= l.id
		WHERE pul.userId = :userId
		""")
	List<Integer> findCourseIdsByUserId(@Param("userId") Integer userId);

	boolean existsById(@NonNull PublicUserLessonIdEntity id);

	@NonNull
	<S extends PublicUserLessonEntity> S save(@NonNull S entity);

	@Query("""
		SELECT COUNT(pul)
		FROM PublicUserLessonEntity pul
		JOIN LessonEntity l ON pul.lessonId = l.id
		WHERE pul.userId = :userId AND l.course.id = :courseId AND pul.status = :status
		""")
	int countByUserIdAndCourseIdAndStatus(
		@Param("userId") Integer userId,
		@Param("courseId") Integer courseId,
		@Param("status") String status
	);

	@NonNull
	Optional<PublicUserLessonEntity> findById(@NonNull PublicUserLessonIdEntity publicUserLessonIdEntity);

	@Query("""
		    SELECT pl
		    FROM PublicUserLessonEntity pl
		        JOIN pl.lessonEntity l
		        JOIN l.course c
		    WHERE
		        pl.publicUserEntity.id = :userId AND
		        c.id = :courseId
		""")
	List<PublicUserLessonEntity> findAllByCourseId(int userId, int courseId);
}
