package org.pacey.dropwizardpebble;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.collect.ImmutableList;
import com.mitchellbosecke.pebble.error.PebbleException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import static javax.ws.rs.core.MediaType.TEXT_HTML_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class PebbleMessageBodyWriterTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	private PebbleViewRenderer pebbleViewRenderer;

	@Mock
	private MetricRegistry metricRegistry;

	@InjectMocks
	private PebbleMessageBodyWriter pebbleMessageBodyWriter;

	@Mock
	private Timer timer;

	@Mock
	private PebbleView pebbleView;

	@Mock
	private OutputStream outputStream;

	@Mock
	private HttpHeaders httpHeaders;

	@Before
	public void defaultMockBehaviour() throws Exception {
		given(metricRegistry.timer(any())).willReturn(timer);
		pebbleMessageBodyWriter.setHeaders(httpHeaders);
	}

	@Test
	public void shouldBeWritableForThePebbleViewClass() {
		boolean writeable = pebbleMessageBodyWriter.isWriteable(PebbleView.class, PebbleView.class, new Annotation[0], TEXT_HTML_TYPE);

		assertThat(writeable).isTrue();
	}

	@Test
	public void shouldBeWritableForAnImplementationOfThePebbleViewClass() {
		boolean writeable = pebbleMessageBodyWriter.isWriteable(PebbleView.class, ConcretePebbleView.class, new Annotation[0], TEXT_HTML_TYPE);

		assertThat(writeable).isTrue();
	}

	@Test
	public void shouldWriteToThePebbleViewRenderer() throws Exception {
		pebbleMessageBodyWriter.writeTo(pebbleView, PebbleView.class, PebbleView.class, new Annotation[0], TEXT_HTML_TYPE, new MultivaluedHashMap<>(), outputStream);

		then(pebbleViewRenderer).should().render(eq(pebbleView), any(), eq(outputStream));
	}

	@Test
	public void shouldPickTheFirstLocaleForRendering() throws Exception {
		given(httpHeaders.getAcceptableLanguages()).willReturn(ImmutableList.of(Locale.UK, Locale.FRENCH));

		pebbleMessageBodyWriter.writeTo(pebbleView, PebbleView.class, PebbleView.class, new Annotation[0], TEXT_HTML_TYPE, new MultivaluedHashMap<>(), outputStream);

		then(pebbleViewRenderer).should().render(any(), eq(Locale.UK), any());
	}

	@Test
	public void shouldUseDefaultLocaleForRenderingWhenNotSpecified() throws Exception {
		given(httpHeaders.getAcceptableLanguages()).willReturn(Collections.emptyList());

		pebbleMessageBodyWriter.writeTo(pebbleView, PebbleView.class, PebbleView.class, new Annotation[0], TEXT_HTML_TYPE, new MultivaluedHashMap<>(), outputStream);

		then(pebbleViewRenderer).should().render(any(), eq(Locale.getDefault()), any());
	}

	@Test
	public void shouldUseDefaultLocaleForRenderingWhenNull() throws Exception {
		given(httpHeaders.getAcceptableLanguages()).willReturn(null);

		pebbleMessageBodyWriter.writeTo(pebbleView, PebbleView.class, PebbleView.class, new Annotation[0], TEXT_HTML_TYPE, new MultivaluedHashMap<>(), outputStream);

		then(pebbleViewRenderer).should().render(any(), eq(Locale.getDefault()), any());
	}

	@Test
	public void shouldUseDefaultLocaleForRenderingWhenNoHttpHeaders() throws Exception {
		pebbleMessageBodyWriter.setHeaders(null);

		pebbleMessageBodyWriter.writeTo(pebbleView, PebbleView.class, PebbleView.class, new Annotation[0], TEXT_HTML_TYPE, new MultivaluedHashMap<>(), outputStream);

		then(pebbleViewRenderer).should().render(any(), eq(Locale.getDefault()), any());
	}

	@Test
	public void shouldThrowAWebApplicationExceptionWhenThereIsAnIssueRendering() throws Exception {
		doThrow(PebbleException.class).when(pebbleViewRenderer).render(any(), any(), any());

		expectedException.expect(WebApplicationException.class);

		pebbleMessageBodyWriter.writeTo(pebbleView, PebbleView.class, PebbleView.class, new Annotation[0], TEXT_HTML_TYPE, new MultivaluedHashMap<>(), outputStream);
	}

	@Test
	public void shouldReturnMinusOneForTheContentSizeAsItCannotBeDeterminedUpFront() throws Exception {
		long size = pebbleMessageBodyWriter.getSize(pebbleView, PebbleView.class, PebbleView.class, new Annotation[0], TEXT_HTML_TYPE);

		assertThat(size).isEqualTo(-1);
	}

	private class ConcretePebbleView implements PebbleView {
		@Override
		public Map<String, Object> getContext() {
			return null;
		}

		@Override
		public String getTemplateName() {
			return null;
		}
	}

}
