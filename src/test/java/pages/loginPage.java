package pages;

import org.openqa.selenium.support.FindBy;
import com.codeborne.selenide.SelenideElement;

public class loginPage {

	@FindBy(name = "userName")
	public SelenideElement userName;

	@FindBy(name = "userPassword")
	public SelenideElement userPassword;

	public void login(String name, String password) {
		userName.clear();
		userPassword.clear();
		userName.sendKeys(name);
		userPassword.sendKeys(password);
		userPassword.pressEnter();

	}
}
