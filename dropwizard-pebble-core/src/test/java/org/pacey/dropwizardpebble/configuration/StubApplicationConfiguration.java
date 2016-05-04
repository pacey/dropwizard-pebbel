package org.pacey.dropwizardpebble.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class StubApplicationConfiguration extends Configuration implements PebbleConfigurable {

	@Valid
	@NotNull
	private PebbleConfiguration pebbleConfiguration;

	@Override
	@JsonProperty("pebble")
	public PebbleConfiguration getPebbleConfiguration() {
		return pebbleConfiguration;
	}

	@JsonProperty("pebble")
	public void setPebbleConfiguration(PebbleConfiguration pebbleConfiguration) {
		this.pebbleConfiguration = pebbleConfiguration;
	}
}
