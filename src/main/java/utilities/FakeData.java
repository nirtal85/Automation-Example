package utilities;

import com.github.javafaker.Faker;
import com.google.inject.Inject;

public class FakeData {
	public Faker faker;

	@Inject
	public FakeData() {
		faker = new Faker();
	}
}
