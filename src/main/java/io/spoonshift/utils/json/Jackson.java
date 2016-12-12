package io.spoonshift.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum Jackson {
	;
	
	private static final ObjectMapper MAPPER;
	
	static {
		MAPPER = new ObjectMapper();
	}

	/**
	 * Returns the deserialized object from the given json string and target
	 * class; or null if the given json string is null.
	 */
	public static <T> T fromJsonString(String json, Class<T> clazz) {
		if (json == null)
			return null;
		try {
			return MAPPER.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Unable to parse Json String.", e);
		}
	}

	public static String toJsonString(Object value) {
		try {
			return MAPPER.writeValueAsString(value);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
