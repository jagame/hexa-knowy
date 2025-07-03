package com.knowy.server.repository;

import com.knowy.server.entity.BannedWordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBannedWordsRepository extends BannedWordsRepository, JpaRepository<BannedWordsEntity, Integer> {

	@Override
	List<BannedWordsEntity> findAll();

	@Query(value = """
		SELECT
			CASE
				WHEN EXISTS (
					SELECT banned_word.word
					FROM banned_word
					WHERE :word ILIKE CONCAT('%', banned_word.word, '%')
				)
				THEN true
				ELSE false
				END AS is_banned;
		""", nativeQuery = true)
	boolean isWordBanned(@Param("word") String word);
}
