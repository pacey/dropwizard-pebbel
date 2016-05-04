package org.pacey.dropwizardpebble.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PebbleConfiguration {

	private String prefix;

	@NotNull
	@NotEmpty
	private String suffix = ".peb";

	private Boolean cacheActive = true;

	@Valid
	private ThreadPoolConfiguration threadPoolConfiguration;

	@Valid
	private CacheConfiguration templateCacheConfiguration;

	@Valid
	private CacheConfiguration tagCacheConfiguration;

	public PebbleConfiguration() {
		this.threadPoolConfiguration = new ThreadPoolConfiguration();
		this.templateCacheConfiguration = new CacheConfiguration();
		this.tagCacheConfiguration = new CacheConfiguration();
	}

	@JsonProperty("prefix")
	public String getPrefix() {
		return prefix;
	}

	@JsonProperty("prefix")
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@JsonProperty("suffix")
	public String getSuffix() {
		return suffix;
	}

	@JsonProperty("suffix")
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@JsonProperty("cacheActive")
	public Boolean getCacheActive() {
		return cacheActive;
	}

	@JsonProperty("cacheActive")
	public void setCacheActive(Boolean cacheActive) {
		this.cacheActive = cacheActive;
	}

	@JsonProperty(ThreadPoolConfiguration.CONFIG_NAME)
	public ThreadPoolConfiguration getThreadPoolConfiguration() {
		return threadPoolConfiguration;
	}

	@JsonProperty(ThreadPoolConfiguration.CONFIG_NAME)
	public void setThreadPoolConfiguration(ThreadPoolConfiguration threadPoolConfiguration) {
		this.threadPoolConfiguration = threadPoolConfiguration;
	}

	@JsonProperty("templateCache")
	public CacheConfiguration getTemplateCacheConfiguration() {
		return templateCacheConfiguration;
	}

	@JsonProperty("templateCache")
	public void setTemplateCacheConfiguration(CacheConfiguration templateCacheConfiguration) {
		this.templateCacheConfiguration = templateCacheConfiguration;
	}

	@JsonProperty("tagCache")
	public CacheConfiguration getTagCacheConfiguration() {
		return tagCacheConfiguration;
	}

	@JsonProperty("tagCache")
	public void setTagCacheConfiguration(CacheConfiguration tagCacheConfiguration) {
		this.tagCacheConfiguration = tagCacheConfiguration;
	}
}
