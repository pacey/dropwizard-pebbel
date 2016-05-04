package org.pacey.dropwizardpebble.template;

import org.pacey.dropwizardpebble.PebbleView;

public class ResourceTemplateResolver implements TemplateResolver {

	private final String suffix;

	public ResourceTemplateResolver(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String resolve(PebbleView pebbleView) {
		final String packagePath = pebbleView.getClass().getPackage().getName().replace('.', '/');
		return String.format("/%s/%s%s", packagePath, pebbleView.getTemplateName(), suffix);
	}
}
