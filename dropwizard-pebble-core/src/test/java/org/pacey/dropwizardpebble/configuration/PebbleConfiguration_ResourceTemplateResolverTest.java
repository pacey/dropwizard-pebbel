package org.pacey.dropwizardpebble.configuration;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Rule;
import org.junit.Test;
import org.pacey.dropwizardpebble.stub.StubApplication;
import org.pacey.dropwizardpebble.stub.StubApplicationConfiguration;

import javax.ws.rs.core.MediaType;

import static com.jayway.restassured.RestAssured.given;
import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static org.hamcrest.Matchers.equalTo;

public class PebbleConfiguration_ResourceTemplateResolverTest {

	@Rule
	public DropwizardAppRule<StubApplicationConfiguration> RULE =
		new DropwizardAppRule<>(StubApplication.class, resourceFilePath("resourceTemplatePathResolver.yaml"));

	@Test
	public void shouldRenderTemplateFromARestStyleResourcePackage() throws Exception {
		given()
			.log().all()
			.get(String.format("http://localhost:%d/stub.html", RULE.getLocalPort()))
			.then().log().all()
			.statusCode(200)
			.header("Content-Type", MediaType.TEXT_HTML)
			.body(equalTo("<p>A rendered pebble template from a resource/REST path - willHaveAValue</p>\n"));
	}

}
