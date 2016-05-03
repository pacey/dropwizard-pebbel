package org.pacey.dropwizardpebble.template;

import org.pacey.dropwizardpebble.PebbleView;

public class DirectoryTemplatePathResolver implements TemplatePathResolver {

	private final String prefix;
	private final String suffix;

	public DirectoryTemplatePathResolver(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Override
	public String resolve(PebbleView pebbleView) {
		return prefix + pebbleView.getTemplateName() + suffix;
	}
}
