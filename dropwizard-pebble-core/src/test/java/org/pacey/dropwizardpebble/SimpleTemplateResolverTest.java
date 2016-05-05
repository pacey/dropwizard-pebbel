package org.pacey.dropwizardpebble;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class SimpleTemplateResolverTest {

	@Mock
	private PebbleView pebbleView;
	private SimpleTemplateResolver simpleTemplateResolver;

	@Before
	public void createClassUnderTest() {
		simpleTemplateResolver = new SimpleTemplateResolver();
	}

	@Test
	public void shouldReturnPrefixWithTemplateNameAndSuffix() throws Exception {
		given(pebbleView.getTemplateName()).willReturn("views/home/homepage");

		String templatePath = simpleTemplateResolver.resolve(pebbleView);

		assertThat(templatePath).isEqualTo("templates/views/home/homepage.peb");
	}
}
