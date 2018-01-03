package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;

public class LoginPage {

	@Inject
	public LoginPage() {
		Configuration.browser = "chrome";
		page(this);
	}

	private SelenideElement email = $("input[name='email']");
	private SelenideElement submit = $("button[type='submit']");
	private SelenideElement password = $("#password");

	public void login(String useremail, String userpassword) {
		email.sendKeys(useremail);
		password.sendKeys(userpassword);
		submit.pressEnter();
	}
}
