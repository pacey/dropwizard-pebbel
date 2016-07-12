package org.pacey.dropwizardpebble;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Rule;
import org.junit.Test;
import org.pacey.dropwizardpebble.stub.StubApplication;
import org.pacey.dropwizardpebble.stub.StubApplicationConfiguration;

import javax.ws.rs.core.MediaType;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LocaleRenderingTest {

	@Rule
	public DropwizardAppRule<StubApplicationConfiguration> RULE =
		new DropwizardAppRule<>(StubApplication.class);

	@Test
	public void shouldUseTheLocaleSpecifiedInTheHttpHeadersEnGB() throws Exception {
		given()
			.header("Accept-Language", "en-gb")
			.get(String.format("http://localhost:%d/internationalisation.html", RULE.getLocalPort()))
			.then()
			.statusCode(200)
			.header("Content-Type", MediaType.TEXT_HTML)
			.body(equalTo("Alright me old mucka"));
	}

	@Test
	public void shouldUseTheLocaleSpecifiedInTheHttpHeadersEnUS() throws Exception {
		given()
			.log().all()
			.header("Accept-Language", "en-us")
			.get(String.format("http://localhost:%d/internationalisation.html", RULE.getLocalPort()))
			.then().log().all()
			.statusCode(200)
			.header("Content-Type", MediaType.TEXT_HTML)
			.body(equalTo("Hello"));
	}
}
