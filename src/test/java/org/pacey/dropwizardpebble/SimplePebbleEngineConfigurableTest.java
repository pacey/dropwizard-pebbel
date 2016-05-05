package org.pacey.dropwizardpebble;

import com.mitchellbosecke.pebble.PebbleEngine;
import io.dropwizard.Configuration;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SimplePebbleEngineConfigurableTest {

	@Mock
	private PebbleEngine.Builder builder;

	@Mock
	private LifecycleEnvironment lifecycleEnvironment;

	@Mock
	private Configuration configuration;

	@Mock
	private PebbleEngine pebbleEngine;

	private SimplePebbleEngineConfigurable<Configuration> simplePebbleEngineConfigurable;

	@Before
	public void createClassUnderTest() throws Exception {
		this.simplePebbleEngineConfigurable = new SimplePebbleEngineConfigurable<>();
	}

	@Test
	public void shouldUseTheBuilderToCreateAPebbleEngine() throws Exception {
		given(builder.build()).willReturn(pebbleEngine);

		PebbleEngine actual = simplePebbleEngineConfigurable.configure(lifecycleEnvironment, configuration, builder);

		assertThat(actual).isEqualTo(pebbleEngine);
	}
}
