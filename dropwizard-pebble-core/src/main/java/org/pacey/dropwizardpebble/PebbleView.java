package org.pacey.dropwizardpebble;

import java.util.Map;

public abstract class PebbleView {
	public abstract Map<String, Object> getContext();
	public abstract String getTemplateName();
}
