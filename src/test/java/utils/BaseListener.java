package utils;

import java.lang.reflect.Method;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import io.qameta.allure.Attachment;
import io.qameta.allure.Issue;
import test.BaseTest;

public class BaseListener implements IInvokedMethodListener {

	private WebDriver driver;

	@Override
	public void beforeInvocation(IInvokedMethod invokedMethod, ITestResult result) {
		Method method = result.getMethod().getConstructorOrMethod().getMethod();
		if (method == null) {
			return;
		}
		if (method.isAnnotationPresent(Issue.class)) {
			throw new SkipException(
					"Open Bug Number: " + method.getAnnotation(Issue.class).value() + " skipping this test");
		}
		return;
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