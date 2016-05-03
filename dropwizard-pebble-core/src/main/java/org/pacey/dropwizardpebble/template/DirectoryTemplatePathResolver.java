package org.pacey.dropwizardpebble.template;

import org.pacey.dropwizardpebble.PebbleView;

public class DirectoryTemplatePathResolver implements TemplatePathResolver {
	@Override
	public String resolve(PebbleView pebbleView) {
		return pebbleView.getTemplateName();
	}
}
