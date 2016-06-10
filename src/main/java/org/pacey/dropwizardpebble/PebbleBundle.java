package org.pacey.dropwizardpebble;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PebbleBundle<T extends Configuration> implements ConfiguredBundle<T> {

	private final PebbleViewRenderer<T> pebbleViewRenderer;

	public PebbleBundle() {
		this(new SimplePebbleEngineConfigurable<>(), new SimpleTemplateResolver());
	}

	public PebbleBundle(PebbleEngineConfigurable<T> pebbleEngineConfigurable, TemplateResolver templateResolver) {
		this.pebbleViewRenderer = new PebbleViewRenderer<>(pebbleEngineConfigurable, templateResolver);
	}

	@Override
	public void initialize(Bootstrap<?> bootstrap) {

	}

	@Override
	public void run(T configuration, Environment environment) throws Exception {
		this.pebbleViewRenderer.configure(environment.lifecycle(), configuration);
		environment.jersey().register(new PebbleMessageBodyWriter(this.pebbleViewRenderer, environment.metrics()));
	}
}
