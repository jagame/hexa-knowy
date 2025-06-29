package com.knowy.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowy.server.util.exception.JsonKnowyException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JsonSerializationService {

	private final ObjectMapper objectMapper;

	public JsonSerializationService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Serializes the given object into its JSON string representation.
	 *
	 * @param obj the object to serialize
	 * @param <T> the type of the object
	 * @return a JSON string representing the object
	 * @throws JsonProcessingException if there is a problem during serialization
	 */
	public <T> String toJson(T obj) throws JsonKnowyException {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new JsonKnowyException("Error serializing object to JSON", e);
		}
	}

	/**
	 * Converts a JSON-like Map representation into an instance of the specified class.
	 *
	 * @param objJson the Map representing the JSON object
	 * @param clazz   the target class to convert to
	 * @param <T>     the type of the target object
	 * @return an instance of the target class with data mapped from the JSON Map
	 */
	public <T> T fromJson(Map<?, ?> objJson, Class<T> clazz) throws JsonKnowyException {
		try {
			return objectMapper.convertValue(objJson, clazz);
		} catch (IllegalArgumentException e) {
			throw new JsonKnowyException("Error converting map to object", e);
		}
	}

	/**
	 * Deserializes the given JSON string into an instance of the specified class.
	 *
	 * @param json  the JSON string to deserialize
	 * @param clazz the target class to convert to
	 * @param <T>   the type of the target object
	 * @return an instance of the target class with data mapped from the JSON string
	 * @throws JsonProcessingException if there is a problem during deserialization
	 */
	public <T> T fromJson(String json, Class<T> clazz) throws JsonKnowyException {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			throw new JsonKnowyException("Error deserializing JSON to object", e);
		}
	}
}
