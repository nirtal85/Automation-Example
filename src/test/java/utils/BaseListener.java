package utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import com.codeborne.selenide.Screenshots;

import io.qameta.allure.Allure;

public class BaseListener implements IInvokedMethodListener {

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod() && !testResult.isSuccess()) {
			attachScreenshot();
		}
	}

	private void attachScreenshot() {
		try {
			Allure.addAttachment("Screenshot on failure",
					new ByteArrayInputStream(FileUtils.readFileToByteArray(Screenshots.takeScreenShotAsFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}