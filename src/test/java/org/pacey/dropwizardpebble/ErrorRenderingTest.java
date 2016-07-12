package org.pacey.dropwizardpebble;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Rule;
import org.junit.Test;
import org.pacey.dropwizardpebble.stub.StubApplication;
import org.pacey.dropwizardpebble.stub.StubApplicationConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ErrorRenderingTest {

	@Rule
	public DropwizardAppRule<StubApplicationConfiguration> RULE =
		new DropwizardAppRule<>(StubApplication.class);

	@Test
	public void shouldReturnASensibleError() throws Exception {
		given()
			.get(String.format("http://localhost:%d/index.html", RULE.getLocalPort()))
			.then()
			.statusCode(500)
			.body(equalTo("<html><head><title>Template Error</title></head><body><h1>Template Error</h1><p>Something went wrong rendering the page</p></body><pre><code>com.mitchellbosecke.pebble.error.LoaderException: Could not find template \"templates/this-name-does-not-exist.peb\" (?:?)</code></pre></html>"));
	}

}
