package com.github.nirtal85.domain;

import java.util.function.Consumer;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class User {
	private String name;
	private String password;

	@Data
	public static class UserBuilder {
		@Accessors(chain = true)
		private String name;
		@Accessors(chain = true)
		private String password;

		public UserBuilder with(Consumer<UserBuilder> builderFunction) {
			builderFunction.accept(this);
			return this;
		}

		public User createUser() {
			User user = new User();
			user.name = this.name;
			user.password = this.password;
			return user;
		}
	}

	private User() {
		// Constructor is now private.
	}
}