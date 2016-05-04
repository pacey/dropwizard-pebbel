package org.pacey.dropwizardpebble.template;

import org.pacey.dropwizardpebble.PebbleView;

public interface TemplateResolver {

	String resolve(PebbleView pebbleView);

}
