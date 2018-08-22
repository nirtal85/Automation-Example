package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.automation.remarks.video.recorder.VideoRecorder;

import io.qameta.allure.Attachment;
import test.BaseTest;

public class AllureAttachment {

	public WebDriver driver;

	@Attachment(value = "video", type = "video/mp4")
	public static byte[] addVideoAttachment() throws InterruptedException {
		try {
			File video = VideoRecorder.getLastRecording();
			Awaitility.await().atMost(5, TimeUnit.SECONDS).pollDelay(1, TimeUnit.SECONDS).ignoreExceptions()
					.until(() -> video != null);
			return Files.readAllBytes(Paths.get(video.getAbsolutePath()));
		} catch (IOException e) {
			return new byte[0];
		}
	}

	@Attachment(value = "{0}", type = "text/plain")
	public static String addTextAttachment(String message) {
		return message;
	}

	@Attachment(value = "Page Screenshot", type = "image/png")
	public byte[] addScreenshotAttachment(ITestResult testResult) {
		Object currentClass = testResult.getInstance();
		driver = ((BaseTest) currentClass).getDriver();
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}
}
