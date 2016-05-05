package org.pacey.dropwizardpebble.stub;

import com.google.common.collect.ImmutableMap;
import org.pacey.dropwizardpebble.PebbleView;

import java.util.Map;

public class StubView implements PebbleView {
	@Override
	public Map<String, Object> getContext() {
		return ImmutableMap.of("someVariable", "willHaveAValue");
	}

	@Override
	public String getTemplateName() {
		return "/stub";
	}
}
