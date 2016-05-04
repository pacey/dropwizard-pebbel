package org.pacey.dropwizardpebble;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.cache.BaseTagCacheKey;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import org.pacey.dropwizardpebble.configuration.CacheConfiguration;
import org.pacey.dropwizardpebble.configuration.PebbleConfiguration;
import org.pacey.dropwizardpebble.configuration.ThreadPoolConfiguration;
import org.pacey.dropwizardpebble.template.DirectoryTemplatePathResolver;
import org.pacey.dropwizardpebble.template.ResourceTemplatePathResolver;
import org.pacey.dropwizardpebble.template.TemplatePathResolver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

public class PebbleViewRenderer {

	private PebbleEngine pebbleEngine;
	private ExecutorService executorService = null;
	private Cache<Object, PebbleTemplate> templateCache = null;
	private Cache<BaseTagCacheKey, Object> tagCache = null;
	private TemplatePathResolver templatePathResolver;

	public void configure(LifecycleEnvironment lifecycleEnvironment, PebbleConfiguration pebbleConfiguration) {
		if (pebbleConfiguration.getThreadPoolConfiguration().getEnabled()) {
			executorService = configureExecutorService(lifecycleEnvironment, pebbleConfiguration.getThreadPoolConfiguration());
		}

		if (pebbleConfiguration.getTemplateCacheConfiguration().getEnabled()) {
			templateCache = configureCache(pebbleConfiguration.getTemplateCacheConfiguration());
		}

		if (pebbleConfiguration.getTagCacheConfiguration().getEnabled()) {
			tagCache = configureCache(pebbleConfiguration.getTagCacheConfiguration());
		}

		if (pebbleConfiguration.getPrefix() != null) {
			templatePathResolver = new DirectoryTemplatePathResolver(pebbleConfiguration.getPrefix(), pebbleConfiguration.getSuffix());
		}
		else {
			templatePathResolver = new ResourceTemplatePathResolver(pebbleConfiguration.getSuffix());
		}

		pebbleEngine = new PebbleEngine.Builder()
			.executorService(executorService)
			.templateCache(templateCache)
			.tagCache(tagCache)
			.loader(new ClasspathLoader())
			.cacheActive(pebbleConfiguration.getCacheActive())
			.build();
	}

	public void render(PebbleView pebbleView, Locale locale, OutputStream output) throws IOException, PebbleException {
		try (final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(output)){
			pebbleEngine.getTemplate(templatePathResolver.resolve(pebbleView)).evaluate(outputStreamWriter, pebbleView.getContext(), locale);
		}
	}

	private <T, V> Cache<T, V> configureCache(CacheConfiguration cacheConfiguration) {
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
			.maximumSize(cacheConfiguration.getSize());

		if (cacheConfiguration.getExpiration() != null) {
			cacheBuilder.expireAfterWrite(cacheConfiguration.getExpiration().getValue(), cacheConfiguration.getExpiration().getUnit());
		}

		return cacheBuilder.build();
	}

	private ExecutorService configureExecutorService(LifecycleEnvironment lifecycleEnvironment, ThreadPoolConfiguration threadPoolConfiguration) {
		return lifecycleEnvironment.executorService("Pebble")
            .minThreads(threadPoolConfiguration.getMin())
            .maxThreads(threadPoolConfiguration.getMax())
            .build();
	}
}
