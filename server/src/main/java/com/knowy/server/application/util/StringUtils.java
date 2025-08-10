package com.knowy.server.application.util;

public class StringUtils {

	private StringUtils() {
	}

	public static boolean isBlank(String text) {
		return text == null || text.isBlank();
	}

	public static boolean isEmpty(String text) {
		return text == null || text.isEmpty();
	}
}
