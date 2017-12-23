package Steps;

import static com.codeborne.selenide.Selenide.title;
import static org.assertj.core.api.Assertions.assertThat;

import cucumber.api.java8.En;

public class Then implements En {

	public Then() throws Throwable {
		Then("^the url is \"([^\"]*)\"$", (String url) -> assertThat(title()).isEqualTo(url));
	}

}
