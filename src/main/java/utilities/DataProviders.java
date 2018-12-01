package utilities;

import org.testng.annotations.DataProvider;

import com.github.javafaker.Faker;
import com.google.inject.Inject;

public class DataProviders {
	@Inject
	Faker faker;

	@DataProvider(name = "users")
	public Object[][] users() {
		return new Object[][] { { faker.internet().emailAddress(), faker.internet().password() } };
	}
}
