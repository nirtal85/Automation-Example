package test;

import static org.assertj.core.api.Assertions.*;

import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import domain.User;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.LoginPage;
import utils.BaseListener;
import utils.DataProviders;

@Guice
public class LoginTest extends BaseTest {

	@Epic("Login")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Login with wrong username and wrong password - expect error")
	@Test(description = "Invalid Login", groups = "Sanity", enabled = true, dataProvider = "users", dataProviderClass = DataProviders.class, invocationCount = 10)
	public void invalidLogin(String email, String password) throws Exception {
		User invalidUser = new User.Builder().setName(email).setPassword(password).create();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAs(invalidUser);
		assertThat(loginPage.getErrorMsg()).contains("Your username is invalid!");
	}

//	@Issue("1")
	@Parameters({ "baseUrl" })
	@Epic("Login")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Login with valid user")
	@Test(description = "valid Login", groups = "Sanity", enabled = true)
	public void validlidLogin(String baseUrl) throws Exception {
		User validUser = new User.Builder().setName(data.getName()).setPassword(data.getPassword()).create();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAs(validUser);
		assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/secure");
	}

	// TODO: example for task scanner
	@Parameters({ "baseUrl" })
	@Epic("Login")
	@Severity(SeverityLevel.CRITICAL)
	@Description("navigate directly to the secure page")
	@Test(description = "unauthorized Login", groups = "Sanity", enabled = true)
	public void unauthorizedLogin(String baseUrl) throws Exception {
		driver.navigate().to(baseUrl + "/secure");
		assertThat(driver.getCurrentUrl()).endsWith("/login");
	}
}