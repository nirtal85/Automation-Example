package test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.title;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.codeborne.selenide.Configuration;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import utils.BaseListener;
import utils.VideoRecord;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;

@Listeners({ BaseListener.class, VideoListener.class })
public class Selenide {
	VideoRecord videoRecord;

	public Selenide() {
		videoRecord = new VideoRecord();
	}

	@Epic("Allure examples")
	@Feature("Testng support")
	@Severity(SeverityLevel.CRITICAL)
	@Video
	@Test(description = "1")
	public void a() {
		testEasy1();
		testEasy2();
		assertThat(title()).isEqualTo("nir");

	}

	@Step
	public void testEasy1() {
		System.out.println(1 + 1);
	}

	@Step
	public void testEasy2() {
		System.out.println(1 + 1);
	}

	@BeforeClass
	@Description("start")
	public void beforeTest() {
		Configuration.browser = "chrome";
		Configuration.startMaximized = false;
		Configuration.screenshots = false;
		Configuration.driverManagerEnabled = true;
		Configuration.fastSetValue = true;
		Configuration.savePageSource = false;
		Configuration.baseUrl = "http://demo.guru99.com";
		open("");
	}

	@AfterMethod
	public void aa() throws InterruptedException {
		videoRecord.attachment();
	}

}