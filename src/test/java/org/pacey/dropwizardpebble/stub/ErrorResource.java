package org.pacey.dropwizardpebble.stub;

import org.pacey.dropwizardpebble.PebbleView;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Collections;
import java.util.Map;

@Resource
@Path("/index.html")
public class ErrorResource {
	@GET
	public PebbleView templateNotFound() {
		return new PebbleView() {
			@Override
			public Map<String, Object> getContext() {
				return Collections.emptyMap();
			}

			@Override
			public String getTemplateName() {
				return "this-name-does-not-exist";
			}
		};
	}
}
