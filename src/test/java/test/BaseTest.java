package test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import com.codeborne.selenide.Configuration;

import io.qameta.allure.Description;
import utils.VideoRecord;

public class BaseTest {
	
	@BeforeSuite(groups = { "sanity" })
	@Description("start")
	public void beforeTest() {
		Configuration.startMaximized = false;
		Configuration.screenshots = false;
		Configuration.driverManagerEnabled = true;
		Configuration.fastSetValue = true;
		Configuration.savePageSource = false;
		Configuration.baseUrl = "http://demo.guru99.com/selenium/guru99home/";
	}

	@AfterMethod(groups = { "sanity" })
	public void aa() throws Exception {
		VideoRecord.attachment();
	}

}
