package utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Data {
	@JsonProperty("name")
	String name;

	@JsonProperty("password")
	String password;

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public static Data get(String filename) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(new File(filename), Data.class);
	}
}
