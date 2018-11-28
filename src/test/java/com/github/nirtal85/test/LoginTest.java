package com.github.nirtal85.test;

import static org.assertj.core.api.Assertions.*;

import org.testng.annotations.Guice;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.github.nirtal85.domain.User;
import com.github.nirtal85.domain.User.UserBuilder;
import com.github.nirtal85.utils.DataProviders;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Guice
@Epic("Login")
public class LoginTest extends BaseTest {

	@Severity(SeverityLevel.CRITICAL)
	@Description("Login with wrong username and wrong password - expect error")
	@Test(description = "Invalid Login", groups = "Sanity", enabled = true, dataProvider = "users", dataProviderClass = DataProviders.class, invocationCount = 10)
	public void invalidLogin(String email, String password) throws Exception {
		User invalidUser = new UserBuilder().with(userBuilder -> {
			userBuilder.setName(email).setPassword(password);
		}).createUser();
		loginPage.loginAs(invalidUser);
		assertThat(loginPage.getErrorMsg()).contains(data.getLoginError());
	}

//	@Issue("1")
	@Parameters({ "baseUrl" })
	@Severity(SeverityLevel.CRITICAL)
	@Description("Login with valid user")
	@Test(description = "valid Login", groups = "Sanity", enabled = true)
	public void validlidLogin(String baseUrl) throws Exception {
		User validUser = new UserBuilder().with(userBuilder -> {
			userBuilder.setName(data.getName()).setPassword(data.getPassword());
		}).createUser();
		loginPage.loginAs(validUser);
		assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/secure");
	}

	// TODO: example for task scanner
	@Parameters({ "baseUrl" })
	@Severity(SeverityLevel.CRITICAL)
	@Description("navigate directly to the secure page")
	@Test(description = "unauthorized Login", groups = "Sanity", enabled = true)
	public void unauthorizedLogin(String baseUrl) throws Exception {
		driver.navigate().to(baseUrl + "/secure");
		assertThat(driver.getCurrentUrl()).endsWith("/login");
	}
}