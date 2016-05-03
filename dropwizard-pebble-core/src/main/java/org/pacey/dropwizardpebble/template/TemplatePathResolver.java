package org.pacey.dropwizardpebble.template;

import org.pacey.dropwizardpebble.PebbleView;

public interface TemplatePathResolver {

    String resolve(PebbleView pebbleView);

}
