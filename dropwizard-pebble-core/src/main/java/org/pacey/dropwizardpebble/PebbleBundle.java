package org.pacey.dropwizardpebble;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.pacey.dropwizardpebble.configuration.PebbleConfigurable;

public class PebbleBundle<T extends Configuration & PebbleConfigurable> implements ConfiguredBundle<T> {

	private final PebbleViewRenderer pebbleViewRenderer;

	public PebbleBundle() {
		this.pebbleViewRenderer = new PebbleViewRenderer();
	}

	@Override
	public void initialize(Bootstrap<?> bootstrap) {

	}

	@Override
	public void run(T configuration, Environment environment) throws Exception {
		this.pebbleViewRenderer.configure(environment.lifecycle(), configuration.getPebbleConfiguration());
		environment.jersey().register(new PebbleMessageBodyWriter(this.pebbleViewRenderer, environment.metrics()));
	}
}
