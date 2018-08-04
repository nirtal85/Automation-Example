package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import io.qameta.allure.Attachment;
import test.BaseTest;

public class BaseListener implements IInvokedMethodListener {

	private WebDriver driver;

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod() && !testResult.isSuccess()) {
			Object currentClass = testResult.getInstance();
			driver = ((BaseTest) currentClass).getDriver();
			saveScreenshotPNG();
		}
	}

	@Attachment(value = "Page Screenshot", type = "image/png")
	public byte[] saveScreenshotPNG() {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}
}