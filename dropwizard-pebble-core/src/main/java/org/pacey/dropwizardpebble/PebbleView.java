package org.pacey.dropwizardpebble;

import java.util.Map;

public interface PebbleView {
	public abstract Map<String, Object> getContext();

	public abstract String getTemplateName();
}
