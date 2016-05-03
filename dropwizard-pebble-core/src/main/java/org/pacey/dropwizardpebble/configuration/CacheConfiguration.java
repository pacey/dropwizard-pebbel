package org.pacey.dropwizardpebble.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

public class CacheConfiguration {

	private Boolean enabled = true;

	@Min(1)
	private Integer size = 200;

	@Valid
	private Expiration expiration;

	@JsonProperty("enabled")
	public Boolean getEnabled() {
		return enabled;
	}

	@JsonProperty("enabled")
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@JsonProperty("size")
	public Integer getSize() {
		return size;
	}

	@JsonProperty("size")
	public void setSize(Integer size) {
		this.size = size;
	}

	@JsonProperty(Expiration.CONFIG_NAME)
	public Expiration getExpiration() {
		return expiration;
	}

	@JsonProperty(Expiration.CONFIG_NAME)
	public void setExpiration(Expiration expiration) {
		this.expiration = expiration;
	}

	public class Expiration {

		static final String CONFIG_NAME = "expiration";

		@Min(1)
		private Long value;

		@NotNull
		private TimeUnit unit;

		@JsonProperty("value")
		public Long getValue() {
			return value;
		}

		@JsonProperty("value")
		public void setValue(Long value) {
			this.value = value;
		}

		@JsonProperty("unit")
		public TimeUnit getUnit() {
			return unit;
		}

		@JsonProperty("unit")
		public void setUnit(TimeUnit unit) {
			this.unit = unit;
		}
	}
}
