package org.pacey.dropwizardpebble;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import static com.codahale.metrics.MetricRegistry.name;

@Provider
@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML})
public class PebbleMessageBodyWriter implements MessageBodyWriter<PebbleView> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageBodyWriter.class);
	private static final String TEMPLATE_ERROR_MSG =
		"<html>" +
			"<head><title>Template Error</title></head>" +
			"<body><h1>Template Error</h1><p>Something went wrong rendering the page</p></body>" +
			"<pre><code>%s</code></pre>" +
			"</html>";

	private final PebbleViewRenderer pebbleViewRenderer;
	private final MetricRegistry metricRegistry;

	private HttpHeaders headers;

	public PebbleMessageBodyWriter(PebbleViewRenderer pebbleViewRenderer, MetricRegistry metricRegistry) {
		this.pebbleViewRenderer = pebbleViewRenderer;
		this.metricRegistry = metricRegistry;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return PebbleView.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(PebbleView pebbleView, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(PebbleView pebbleView, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		try (Timer.Context ignored = metricRegistry.timer(name(type.getClass(), "rendering")).time()) {
			pebbleViewRenderer.render(pebbleView, detectLocale(headers), entityStream);
		} catch (Exception e) {
			LOGGER.error("Failed to render pebble template", e);
			throw new WebApplicationException(Response.serverError()
				.type(MediaType.TEXT_HTML_TYPE)
				.entity(String.format(TEMPLATE_ERROR_MSG, e))
				.build());
		}
	}

	@Context
	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	private Locale detectLocale(HttpHeaders headers) {
		final Locale defaultLocale = Locale.getDefault();

		if (headers == null) {
			return defaultLocale;
		}

		if (headers.getAcceptableLanguages() == null) {
			return defaultLocale;
		}

		final List<Locale> languages = headers.getAcceptableLanguages();
		return languages.stream().findFirst().orElse(defaultLocale);
	}
}
