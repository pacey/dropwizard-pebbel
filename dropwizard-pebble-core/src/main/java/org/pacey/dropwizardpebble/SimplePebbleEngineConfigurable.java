package org.pacey.dropwizardpebble;

import com.mitchellbosecke.pebble.PebbleEngine;
import io.dropwizard.Configuration;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;

public class SimplePebbleEngineConfigurable<T extends Configuration> implements PebbleEngineConfigurable<T> {
	@Override
	public PebbleEngine configure(LifecycleEnvironment lifecycleEnvironment, T configuration, PebbleEngine.Builder builder) {
		return builder.build();
	}
}
