package utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

public class Data {
	@Getter
	private String name;
	@Getter
	private String password;
	@Getter
	private String loginError;
	@Getter
	private String gridURL;

	@JsonProperty("errors")
	private void unpackNested(Map<String, Object> errors) {
		loginError = (String) errors.get("invalidLogin");
	}

	public static Data get(String filename) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(new File(filename), Data.class);
	}
}
