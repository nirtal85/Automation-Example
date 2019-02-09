package ui;

import static org.assertj.core.api.Assertions.*;

import org.testng.annotations.Guice;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import domain.User;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import utilities.DataProviders;

@Guice
@Severity(SeverityLevel.CRITICAL)
@Epic("Login")
public class LoginTest extends BaseTest {

	
	@Description("Login with wrong username and wrong password - expect error")
	@Test(description = "Invalid Login", groups = "Sanity", enabled = true, dataProvider = "users", dataProviderClass = DataProviders.class, invocationCount = 10)
	public void invalidLogin(String email, String password) throws Exception {
		User invalidUser = User.builder().name(email).password(password).build();
		loginPage.loginAs(invalidUser);
		assertThat(loginPage.getErrorMsg()).contains(data.getLoginError());
	}

//	@Issue("1")
	@Parameters({ "baseUrl" })
	@Description("Login with valid user")
	@Test(description = "valid Login", groups = "Sanity", enabled = true)
	public void validlidLogin(String baseUrl) throws Exception {
		User validUser = User.builder().name(data.getAdminName()).password(data.getAdminPassword()).build();
		loginPage.loginAs(validUser);
		assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/secure");
	}

	// TODO: example for task scanner
	@Parameters({ "baseUrl" })
	@Description("navigate directly to the secure page")
	@Test(description = "unauthorized Login", groups = "Sanity", enabled = true)
	public void unauthorizedLogin(String baseUrl) throws Exception {
		driver.navigate().to(baseUrl + "/secure");
		assertThat(driver.getCurrentUrl()).endsWith("/login");
	}
}