package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import domain.User;
import io.qameta.allure.Step;
import lombok.NonNull;

public class LoginPage {
	protected WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "username")
	private WebElement userName;

	@FindBy(id = "password")
	private WebElement userPassword;

	@FindBy(id = "flash")
	private WebElement errorMsg;

	public String getErrorMsg() {
		return errorMsg.getText();
	}

	@Step("Login with User Name: {user.name} And Password: {user.password}")
	public LoginPage loginAs(@NonNull User user) {
		userName.clear();
		userPassword.clear();
		userName.sendKeys(user.getName());
		userPassword.sendKeys(user.getPassword());
		userPassword.sendKeys(Keys.ENTER);
		return this;
	}
}
