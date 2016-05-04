package org.pacey.dropwizardpebble.template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.pacey.dropwizardpebble.PebbleView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DirectoryTemplatePathResolverTest {

	@Mock
	private PebbleView pebbleView;

	@Test
	public void shouldResolveTemplatePathToADirectory() throws Exception {
		given(pebbleView.getTemplateName()).willReturn("/views/homepage");

		final DirectoryTemplateResolver directoryTemplatePathResolver = new DirectoryTemplateResolver("/templates", ".peb");
		String templatePath = directoryTemplatePathResolver.resolve(pebbleView);

		assertThat(templatePath).isEqualTo("templates/views/homepage.peb");
	}

}
