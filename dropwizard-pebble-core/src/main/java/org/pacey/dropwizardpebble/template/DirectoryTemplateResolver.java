package org.pacey.dropwizardpebble.template;

import org.pacey.dropwizardpebble.PebbleView;

public class DirectoryTemplateResolver implements TemplateResolver {

	private final String prefix;
	private final String suffix;

	public DirectoryTemplateResolver(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Override
	public String resolve(PebbleView pebbleView) {
		String templateUri = prefix + pebbleView.getTemplateName() + suffix;
		if (templateUri.startsWith("/")) {
			templateUri = templateUri.replaceFirst("/", "");
		}
		return templateUri;
	}
}
