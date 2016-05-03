package org.pacey.dropwizardpebble.template;

import org.pacey.dropwizardpebble.PebbleView;

public class ResourceTemplatePathResolver implements TemplatePathResolver {

	private final String suffix;

	public ResourceTemplatePathResolver(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String resolve(PebbleView pebbleView) {
		final String packagePath = pebbleView.getClass().getPackage().getName().replace('.', '/');
		return String.format("/%s/%s%s", packagePath, pebbleView.getTemplateName(), suffix);
	}
}
