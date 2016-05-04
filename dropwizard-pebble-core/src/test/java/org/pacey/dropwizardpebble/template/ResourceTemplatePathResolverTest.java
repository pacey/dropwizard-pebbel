package org.pacey.dropwizardpebble.template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.pacey.dropwizardpebble.PebbleView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ResourceTemplatePathResolverTest {

	@Mock
	private PebbleView pebbleView;

	@Test
	public void shouldResolveTemplatePathToAResourceDirectory() throws Exception {
		given(pebbleView.getTemplateName()).willReturn("/the-best-view-ever");

		final ResourceTemplateResolver resourceTemplatePathResolver = new ResourceTemplateResolver(".peb");
		String templatePath = resourceTemplatePathResolver.resolve(pebbleView);

		assertThat(templatePath).isEqualTo("org/pacey/dropwizardpebble/the-best-view-ever.peb");
	}

}
