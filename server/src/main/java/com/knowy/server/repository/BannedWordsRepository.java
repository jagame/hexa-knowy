package com.knowy.server.repository;

import com.knowy.server.entity.BannedWordsEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BannedWordsRepository {
	List<BannedWordsEntity> findAll();
	boolean isWordBanned(@Param("word") String word);
}
