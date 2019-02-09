package utilities;

import java.lang.reflect.Method;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;

import io.qameta.allure.Attachment;
import io.qameta.allure.Issue;

public class ListenerClass extends TestListenerAdapter {
	@Attachment(value = "Page Screenshot", type = "image/png")
	public byte[] captureScreenshot(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@Override
	public void onTestStart(ITestResult result) {
		Method method = result.getMethod().getConstructorOrMethod().getMethod();
		if (method == null) {
			return;
		}
		if (method.isAnnotationPresent(Issue.class)) {
			throw new SkipException(
					String.format("Open bug number %s skipping this test", method.getAnnotation(Issue.class).value()));
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Object webDriverAttribute = result.getTestContext().getAttribute("WebDriver");
		captureScreenshot((WebDriver) webDriverAttribute);
	}
}