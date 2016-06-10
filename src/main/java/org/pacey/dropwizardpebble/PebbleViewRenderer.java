package org.pacey.dropwizardpebble;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import io.dropwizard.Configuration;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Locale;

public class PebbleViewRenderer<T extends Configuration> implements ViewRenderer<T> {

	private final PebbleEngineConfigurable<T> pebbleEngineConfigurable;
	private final TemplateResolver templateResolver;
	private PebbleEngine pebbleEngine;

	public PebbleViewRenderer(PebbleEngineConfigurable<T> pebbleEngineConfigurable, TemplateResolver templateResolver) {
		this.pebbleEngineConfigurable = pebbleEngineConfigurable;
		this.templateResolver = templateResolver;
	}

	public void configure(LifecycleEnvironment lifecycleEnvironment, T configuration) {
		final PebbleEngine.Builder builder = new PebbleEngine.Builder();
		this.pebbleEngine = pebbleEngineConfigurable.configure(lifecycleEnvironment, configuration, builder);
	}

	public void render(PebbleView pebbleView, Locale locale, OutputStream output) throws IOException, PebbleException {
		try (final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(output, Charset.forName("UTF-8"))) {
			pebbleEngine.getTemplate(templateResolver.resolve(pebbleView)).evaluate(outputStreamWriter, pebbleView.getContext(), locale);
		}
	}
}
