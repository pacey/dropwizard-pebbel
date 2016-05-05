package org.pacey.dropwizardpebble;

public class SimpleTemplateResolver implements TemplateResolver {

	private static final String PREFIX = "templates/";
	private static final String SUFFIX = ".peb";

	@Override
	public String resolve(PebbleView pebbleView) {
		final String templateName = pebbleView.getTemplateName();
		return PREFIX.concat(templateName).concat(SUFFIX);
	}
}
