package org.pacey.dropwizardpebble;

import com.mitchellbosecke.pebble.PebbleEngine;
import io.dropwizard.Configuration;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;

public interface PebbleEngineConfigurable<T extends Configuration> {
	PebbleEngine configure(LifecycleEnvironment lifecycleEnvironment, T configuration, final PebbleEngine.Builder builder);
}
