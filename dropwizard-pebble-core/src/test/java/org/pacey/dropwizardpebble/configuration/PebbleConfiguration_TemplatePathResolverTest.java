package org.pacey.dropwizardpebble.configuration;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static com.jayway.restassured.RestAssured.given;
import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static org.hamcrest.Matchers.equalTo;

public class PebbleConfiguration_TemplatePathResolverTest {

	@Rule
	public DropwizardAppRule<StubApplicationConfiguration> RULE =
		new DropwizardAppRule<>(StubApplication.class, resourceFilePath("templatePathResolver.yaml"));

	@Test
	public void shouldRenderTemplateFromTemplateDirectory() throws Exception {
		given()
			.log().all()
			.get(String.format("http://localhost:%d/stub.html", RULE.getLocalPort()))
			.then().log().all()
			.statusCode(200)
			.header("Content-Type", MediaType.TEXT_HTML)
			.body(equalTo("<p>Some stubbing here</p>\n"));
	}

}
