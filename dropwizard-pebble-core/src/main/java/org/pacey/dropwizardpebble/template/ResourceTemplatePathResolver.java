package org.pacey.dropwizardpebble.template;

import org.pacey.dropwizardpebble.PebbleView;

public class ResourceTemplatePathResolver implements TemplatePathResolver {
    @Override
    public String resolve(PebbleView pebbleView) {
        final String packagePath = pebbleView.getClass().getPackage().getName().replace('.', '/');
        return String.format("/%s/%s", packagePath, pebbleView.getTemplateName());
    }
}
