package com.knowy.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JsonService {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public <T> String toJson(T obj) throws JsonProcessingException {
		return objectMapper.writeValueAsString(obj);
	}

	public <T> T fromJson(Map objJson, Class<T> clazz) throws JsonProcessingException {
		return objectMapper.convertValue(objJson, clazz);
	}
}
