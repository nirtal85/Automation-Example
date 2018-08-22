package utils;

import java.lang.reflect.Method;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import io.qameta.allure.Issue;

public class BaseListener implements IInvokedMethodListener {

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
			AllureAttachment allureAttachment = new AllureAttachment();
			allureAttachment.addScreenshotAttachment(testResult);
		}
	}

}