package utilities;

import java.io.File;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.SneakyThrows;

public class Data {
	@Getter
	private String loginError;
	@Getter
	private String adminPassword;
	@Getter
	private String adminName;
	@Getter
	private String gridURL;

	@JsonProperty("errors")
	private void unpackNestedErrors(Map<String, Object> errors) {
		this.loginError = (String) errors.get("invalidLogin");
	}

	@JsonProperty("users")
	private void unpackNestedUsers(Map<String, Object> user) {
		Map<String, String> adminUser = (Map<String, String>) user.get("admin");
		this.adminName = adminUser.get("name");
		this.adminPassword = adminUser.get("password");
	}

	@SneakyThrows
	public static Data get(String filename)  {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(new File(filename), Data.class);
	}
}
