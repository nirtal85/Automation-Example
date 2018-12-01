package utilities;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;

import driver.DriverType;
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
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {
		if (method.isTestMethod() && !result.isSuccess()) {
			ITestContext context = result.getTestContext();
			DriverType driverType = DriverType.valueOf(context.getCurrentXmlTest().getParameter("driverType"));
			AllureAttachment allureAttachment = new AllureAttachment(driverType);

			try {
				allureAttachment.addScreenshotAttachment(context, result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}