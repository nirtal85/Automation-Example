package domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
	private String name;
	private String password;
}