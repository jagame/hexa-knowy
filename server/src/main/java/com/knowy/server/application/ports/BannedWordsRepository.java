package com.knowy.server.application.ports;

public interface BannedWordsRepository {
	boolean isWordBanned(String word);
}
