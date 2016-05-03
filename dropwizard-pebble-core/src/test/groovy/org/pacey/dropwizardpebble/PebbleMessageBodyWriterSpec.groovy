package org.pacey.dropwizardpebble

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.Timer
import spock.lang.Specification
import spock.lang.Unroll

import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MultivaluedMap
import java.lang.annotation.Annotation
import java.lang.reflect.Type

import static javax.ws.rs.core.MediaType.TEXT_HTML_TYPE

@Unroll
class PebbleMessageBodyWriterSpec extends Specification {

	def "isWritable should be #writable for #clazz"() {
		given:
		def pebbleMessageBodyWriter = new PebbleMessageBodyWriter(Mock(PebbleViewRenderer.class), Mock(MetricRegistry.class))

		when:
		def result = pebbleMessageBodyWriter.isWriteable(clazz, Mock(Type.class), new Annotation[0], TEXT_HTML_TYPE)

		then:
		result == writable

		where:
		clazz            || writable
		PebbleView.class || true
		Long.class       || false
	}

	def "should call through to the PebbleViewRenderer"() {
		given:
		def pebbleViewRenderer = Mock(PebbleViewRenderer.class)
		def metricRegistry = Mock(MetricRegistry.class)
		metricRegistry.timer(_) >> Mock(Timer.class)
		def pebbleMessageBodyWriter = new PebbleMessageBodyWriter(pebbleViewRenderer, metricRegistry)

		when:
		def pebbleView = Mock(PebbleView.class)
		def outputStream = Mock(OutputStream.class)
		pebbleMessageBodyWriter.writeTo(pebbleView, PebbleView.class, Mock(Type.class), new Annotation[0], TEXT_HTML_TYPE, Mock(MultivaluedMap.class), outputStream)

		then:
		1 * pebbleViewRenderer.render(*_)
	}

	def "should use Locale #locale when available locales are #locales"() {
		given:
		def pebbleViewRenderer = Mock(PebbleViewRenderer.class)
		def metricRegistry = Mock(MetricRegistry.class)
		metricRegistry.timer(_) >> Mock(Timer.class)
		def pebbleMessageBodyWriter = new PebbleMessageBodyWriter(pebbleViewRenderer, metricRegistry)
		def httpHeaders = Mock(HttpHeaders.class)
		httpHeaders.getAcceptableLanguages() >> locales
		pebbleMessageBodyWriter.setHeaders(httpHeaders)

		when:
		pebbleMessageBodyWriter.writeTo(Mock(PebbleView.class), PebbleView.class, Mock(Type.class), new Annotation[0], TEXT_HTML_TYPE, Mock(MultivaluedMap.class), Mock(OutputStream.class))

		then:
		1 * pebbleViewRenderer.render(*_) >> { arguments ->
			final Locale selectedLocale = arguments[1] as Locale
			assert selectedLocale == locale
		}

		where:
		locales                || locale
		[Locale.UK]            || Locale.UK
		[Locale.US, Locale.UK] || Locale.US
		[]                     || Locale.default
		null                   || Locale.default
	}

}
