package org.pacey.dropwizardpebble.stub;

import org.pacey.dropwizardpebble.PebbleView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Map;

@Path("/internationalisation.html")
@Produces(MediaType.TEXT_HTML)
public class InternationalisationResource {

	@GET
	public PebbleView get() {
		return new PebbleView() {
			@Override
			public Map<String, Object> getContext() {
				return Collections.emptyMap();
			}

			@Override
			public String getTemplateName() {
				return "/internationalisation";
			}
		};
	}

}
