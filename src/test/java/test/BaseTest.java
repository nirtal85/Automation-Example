package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import com.google.common.io.Files;
import driver.DriverManager;
import driver.DriverManagerFactory;
import driver.DriverType;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import utils.Data;

public class BaseTest {
	public WebDriver driver;
	DriverManager driverManager;

	public Data data;

	@Parameters({ "baseUrl", "data-file" })
	@BeforeMethod(alwaysRun = true)
	public void beforeClass(String baseUrl, String dataFile) throws Exception {
		driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
		driver = driverManager.getDriver();
		driver.navigate().to(baseUrl);
		data = Data.get(dataFile);
	}

	@Parameters({ "baseUrl" })
	@AfterMethod(alwaysRun = true)
	public void cleanUp(String baseUrl) {
		String sessionId = getSessionId();
		driverManager.quitDriver();
		attachAllureVideo(sessionId);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public URL getVideoUrl() {
		return getVideoUrl(getSessionId());
	}

	public static URL getVideoUrl(String sessionId) {
		URL url = null;
		try {
			url = new URL(selenoidUrl + "/video/" + sessionId + ".mp4");
		} catch (Exception e) {
			System.out.println("getVideoUrl");
			e.printStackTrace();
		}
		return url;
	}

	private static String selenoidUrl = "http://127.0.0.1:4444";

	public static void attachAllureVideo(String sessionId) {
		try {
			URL videoUrl = new URL(selenoidUrl + "/video/" + sessionId + ".mp4");
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

	@Attachment(value = "video", type = "video/mp4", fileExtension = ".mp4")
	public static byte[] attachVideo(String sessionId) {
		try {
			File mp4 = new File(System.getProperty("java.io.tmpdir") + "temp.mp4");
			mp4.deleteOnExit();
			FileUtils.copyURLToFile(getVideoUrl(sessionId), mp4);
			return Files.toByteArray(mp4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	public String getSessionId() {
		return ((RemoteWebDriver) getDriver()).getSessionId().toString();
	}
}
