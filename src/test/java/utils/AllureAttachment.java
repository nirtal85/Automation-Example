package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import driver.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import test.BaseTest;

public class AllureAttachment {
	public WebDriver driver;
	static String videoURL;

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

	public void attachAllureVideo(String sessionId, ITestContext context) {
		try {
			URL videoUrl = new URL(DriverManager.getGridURL(context) + "/video/" + sessionId + ".mp4");
			InputStream is = getSelenoidVideo(videoUrl);
			Allure.addAttachment("Video", "video/mp4", is, "mp4");
//			deleteSelenoidVideo(videoUrl);
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

	public static void CleanSelenoidVideos(ITestContext context)
			throws JsonParseException, JsonMappingException, IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		List<String> videos = Arrays.asList(
				Jsoup.connect(DriverManager.getGridURL(context) + "/video/").get().body().text().split("\\r?\\n"));
		videos.forEach(video -> {
			try {
				videoURL = DriverManager.getGridURL(context) + "/video/" + video;
				HttpDelete httpDelete = new HttpDelete(videoURL);
				httpDelete.setHeader("Content-Type", "application/x-www-form-urlencoded");
				System.out.println(httpClient.execute(httpDelete).getStatusLine().getStatusCode());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
