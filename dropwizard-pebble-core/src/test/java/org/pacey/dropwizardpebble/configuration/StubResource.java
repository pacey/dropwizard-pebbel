package org.pacey.dropwizardpebble.configuration;

import org.pacey.dropwizardpebble.PebbleView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/stub.html")
@Produces(MediaType.TEXT_HTML)
public class StubResource {

	@GET
	public PebbleView getStub() {
		return new StubView();
	}

}
