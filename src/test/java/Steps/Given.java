package Steps;

import static com.codeborne.selenide.Selenide.title;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

import cucumber.api.java8.En;
import pages.loginPage;

public class Given implements En {
	loginPage loginPage;

	public Given() throws Throwable {
		loginPage loginPage = page(loginPage.class);

		Then("^the url is \"([^\"]*)\"$", (String url) -> assertThat(title()).isEqualTo(url));

		Given("^I login with user \"([^\"]*)\" and password \"([^\"]*)\"$", (String userName, String password) -> 
			loginPage.login(userName, password));

		Then("^the text on elemenet \"([^\"]*)\" should be \"([^\"]*)\"$", (SelenideElement element, String expectedText) -> 
		assertThat(element.text()).isEqualTo(expectedText));
		}
}
