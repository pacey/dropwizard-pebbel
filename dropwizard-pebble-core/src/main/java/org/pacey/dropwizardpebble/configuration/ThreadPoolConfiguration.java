package org.pacey.dropwizardpebble.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ThreadPoolConfiguration {

	static final String CONFIG_NAME = "threads";

	private Boolean enabled = false;

	@Min(0)
	@Max(20)
	private Integer min = 0;

	@Min(0)
	@Max(20)
	private Integer max = 0;

	@JsonProperty("enabled")
	public Boolean getEnabled() {
		return enabled;
	}

	@JsonProperty("enabled")
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@JsonProperty("min")
	public Integer getMin() {
		return min;
	}

	@JsonProperty("min")
	public void setMin(Integer min) {
		this.min = min;
	}

	@JsonProperty("max")
	public Integer getMax() {
		return max;
	}

	@JsonProperty("max")
	public void setMax(Integer max) {
		this.max = max;
	}
}
