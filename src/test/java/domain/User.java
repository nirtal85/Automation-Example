
package domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class User {
	private String name;
	private String password;

	@Data
	public static class Builder {
		@Accessors(chain = true)
		private String name;
		@Accessors(chain = true)
		private String password;

		public User create() {
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