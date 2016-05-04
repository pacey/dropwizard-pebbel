package org.pacey.dropwizardpebble.configuration;

import org.pacey.dropwizardpebble.PebbleView;

public class StubView extends PebbleView {
	@Override
	public String getTemplateName() {
		return "/stub";
	}
}
