package domain;

import java.util.function.Consumer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

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
}