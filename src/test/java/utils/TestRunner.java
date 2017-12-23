package utils;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.api.testng.CucumberFeatureWrapper;

import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.codeborne.selenide.Configuration;

@CucumberOptions(features = "Feature",
		// glue = {"testproject.testproject"},
		glue = { "Steps" }, tags = { "~@Ignore" }, monochrome = true, plugin = {
				"html:target/cucumber-reports/cucumber-pretty",
				"json:target/cucumber-reports/CucumberTestReport.json", })
@Listeners({ BaseListener.class })
public class TestRunner {
	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		Configuration.browser = "chrome";
		open("http://demo.guru99.com/selenium/guru99home/");
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		testNGCucumberRunner.finish();
	}
}