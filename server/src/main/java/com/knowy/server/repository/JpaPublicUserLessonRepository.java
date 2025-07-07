package com.knowy.server.repository;

import com.knowy.server.entity.PublicUserLessonEntity;
import com.knowy.server.entity.PublicUserLessonIdEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPublicUserLessonRepository extends PublicUserLessonRepository, JpaRepository<PublicUserLessonEntity, PublicUserLessonIdEntity> {

	@Override
	boolean existsByUserIdAndLessonId(Integer userId, Integer lessonId);

	@Override
	@Query("""
		SELECT DISTINCT l.course.id
		FROM PublicUserLessonEntity pul
		JOIN LessonEntity l ON pul.lessonId= l.id
		WHERE pul.userId = :userId
		""")
	List<Integer> findCourseIdsByUserId(@Param("userId") Integer userId);


	@Override
	boolean existsById(PublicUserLessonIdEntity id);

	@Override
	@NonNull
	<S extends PublicUserLessonEntity> S save(@NonNull S entity);

	@Override
	@Query("""
        SELECT COUNT(pul)
        FROM PublicUserLessonEntity pul
        JOIN LessonEntity l ON pul.lessonId = l.id
        WHERE pul.userId = :userId AND l.course.id = :courseId AND pul.status = :status
        """)
	int countByUserIdAndCourseIdAndStatus(@Param("userId") Integer userId,
										  @Param("courseId") Integer courseId,
										  @Param("status") String status);

}
