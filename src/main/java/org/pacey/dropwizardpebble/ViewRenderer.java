package org.pacey.dropwizardpebble;

import com.mitchellbosecke.pebble.error.PebbleException;
import io.dropwizard.Configuration;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public interface ViewRenderer<T extends Configuration> {
	void configure(LifecycleEnvironment lifecycleEnvironment, T configuration);

	void render(PebbleView pebbleView, Locale locale, OutputStream output) throws IOException, PebbleException;
}
