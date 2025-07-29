package com.knowy.server.application.ports;

import org.springframework.data.repository.query.Param;

public interface BannedWordsRepository {
	boolean isWordBanned(@Param("word") String word);
}
