package org.pacey.dropwizardpebble.stub;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.pacey.dropwizardpebble.PebbleBundle;

public class StubApplication extends Application<StubApplicationConfiguration> {

	public static void main(String[] args) throws Exception {
		new StubApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<StubApplicationConfiguration> bootstrap) {
		bootstrap.addBundle(new PebbleBundle<>());
	}

	@Override
	public void run(StubApplicationConfiguration configuration, Environment environment) throws Exception {
		environment.jersey().register(new StubResource());
		environment.jersey().register(new InternationalisationResource());
	}
}
