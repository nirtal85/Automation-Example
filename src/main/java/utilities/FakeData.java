package utilities;

import javax.inject.Singleton;

import com.github.javafaker.Faker;
import com.google.inject.Inject;

public class FakeData {
	protected Faker faker;

	@Inject
	@Singleton
	public FakeData() {
		faker = new Faker();
	}
}
