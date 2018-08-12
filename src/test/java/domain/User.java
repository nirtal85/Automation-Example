package domain;

import lombok.Data;

@Data
public class User {
	String name;
	String password;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
}
