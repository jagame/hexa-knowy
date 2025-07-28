package com.knowy.server.application.ports;

import com.knowy.server.infrastructure.adapters.repository.entity.BannedWordsEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BannedWordsRepository {
	List<BannedWordsEntity> findAll();

	boolean isWordBanned(@Param("word") String word);
}
