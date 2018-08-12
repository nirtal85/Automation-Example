package test;

import static org.assertj.core.api.Assertions.*;

import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.github.javafaker.Faker;
import com.google.inject.Inject;

import domain.User;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.LoginPage;
import utils.BaseListener;

@Guice
@Listeners({ BaseListener.class, VideoListener.class })
public class LoginTest extends BaseTest {
	@Inject
	Faker faker;

	@Video
	@Epic("Login")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Login test with wrong username and wrong password")
	@Test(description = "Invalid Login", groups = "Sanity", enabled = true)
	public void invalidLogin() throws Exception {
		User invalidUser = new User(faker.internet().emailAddress(), faker.internet().password());
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(invalidUser);
		assertThat(loginPage.getErrorMsg()).contains("Your username is invalid!");
	}

//	@Issue("1")
	@Parameters({ "baseUrl" })
	@Epic("Login")
	@Video
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "valid Login", groups = "Sanity", enabled = true)
	public void validlidLogin(String baseUrl) throws Exception {
		User validUser = new User(data.getName(), data.getPassword());
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(validUser);
		assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/secure");
	}

	@Parameters({ "baseUrl" })
	@Epic("Login")
	@Video
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "unauthorized Login", groups = "Sanity", enabled = true, retryAnalyzer = utils.RetryAnalyzer.class)
	public void unauthorizedLogin(String baseUrl) throws Exception {
		driver.navigate().to(baseUrl + "/secure");
		assertThat(driver.getCurrentUrl()).endsWith("/login");
	}
}