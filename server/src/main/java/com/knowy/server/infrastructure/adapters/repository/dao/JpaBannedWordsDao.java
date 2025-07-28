package com.knowy.server.infrastructure.adapters.repository.dao;

import com.knowy.server.application.ports.BannedWordsRepository;
import com.knowy.server.infrastructure.adapters.repository.entity.BannedWordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBannedWordsDao extends JpaRepository<BannedWordsEntity, Integer> {

	@Query(value = """
		SELECT
		    CASE
		        WHEN EXISTS (
		            SELECT bw.word
		            FROM banned_word bw
		            WHERE :word ILIKE CONCAT('%', bw.word, '%')
		        )
		        THEN true
		        ELSE false
		    END AS is_banned
		""", nativeQuery = true)
	boolean isWordBanned(@Param("word") String word);
}
