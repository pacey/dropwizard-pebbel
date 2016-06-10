package org.pacey.dropwizardpebble;

import java.util.Map;

public interface PebbleView {
	Map<String, Object> getContext();

	String getTemplateName();
}
