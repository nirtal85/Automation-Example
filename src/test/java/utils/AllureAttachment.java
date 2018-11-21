package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.io.Files;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import test.BaseTest;

public class AllureAttachment {
	public Data data;
	public WebDriver driver;
	
	public String gridURL(ITestContext context) throws JsonParseException, JsonMappingException, IOException {
		data = Data.get(context.getCurrentXmlTest().getParameter("data-file"));
		return data.getGridURL();
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

	@Attachment(value = "video", type = "video/mp4", fileExtension = ".mp4")
	public byte[] attachVideo(String sessionId, ITestContext context) {
		try {
			File mp4 = new File(System.getProperty("java.io.tmpdir") + "temp.mp4");
			mp4.deleteOnExit();
			FileUtils.copyURLToFile(getVideoUrl(sessionId, context), mp4);
			return Files.toByteArray(mp4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	public URL getVideoUrl(ITestResult testResult, ITestContext context) {
		return getVideoUrl(getSessionId(testResult, context), context);
	}

	public URL getVideoUrl(String sessionId, ITestContext context) {
		URL url = null;
		try {
			url = new URL(gridURL(context) + "/video/" + sessionId + ".mp4");
		} catch (Exception e) {
			System.out.println("getVideoUrl");
			e.printStackTrace();
		}
		return url;
	}


	public void attachAllureVideo(String sessionId, ITestContext context) {
		try {
			URL videoUrl = new URL(gridURL(context) + "/video/" + sessionId + ".mp4");
			InputStream is = getSelenoidVideo(videoUrl);
			Allure.addAttachment("Video", "video/mp4", is, "mp4");
			deleteSelenoidVideo(videoUrl);
		} catch (Exception e) {
			System.out.println("attachAllureVideo");
			e.printStackTrace();
		}
	}

	public static InputStream getSelenoidVideo(URL url) {
		int lastSize = 0;
		int exit = 1;
		for (int i = 0; i < 20; i++) {
			try {
				int size = Integer.parseInt(url.openConnection().getHeaderField("Content-Length"));
				System.out.println("Content-Length: " + size);
				System.out.println(i);
				if (size > lastSize) {
					lastSize = size;
					Thread.sleep(2000);
				} else if (size == lastSize) {
					exit--;
					Thread.sleep(2000);
				}
				if (exit < 0) {
					return url.openStream();
				}
			} catch (Exception e) {
				System.out.println("getSelenoidVideo");
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void deleteSelenoidVideo(URL url) {
		try {
			HttpURLConnection deleteConn = (HttpURLConnection) url.openConnection();
			deleteConn.setDoOutput(true);
			deleteConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			deleteConn.setRequestMethod("DELETE");
			deleteConn.connect();
			deleteConn.disconnect();
		} catch (IOException e) {
			System.out.println("deleteSelenoidVideo");
			e.printStackTrace();
		}
	}

	public synchronized String getSessionId(ITestResult testResult, ITestContext context) {
		Object currentClass = testResult.getInstance();
		return ((RemoteWebDriver)((BaseTest) currentClass).getDriver()).getSessionId().toString();
	}
}
