package org.pacey.dropwizardpebble.template

import org.pacey.dropwizardpebble.PebbleView
import spock.lang.Specification

class ResourceTemplatePathResolverSpec extends Specification {

    def "should resolve template path to a resource directory"() {
        given:
        final PebbleView pebbleView = Mock(PebbleView.class)
        pebbleView.getTemplateName() >> "the-best-view-ever"

        when:
        final ResourceTemplatePathResolver resourceTemplatePathResolver = new ResourceTemplatePathResolver()
        def templatePath = resourceTemplatePathResolver.resolve(pebbleView)

        then:
        templatePath == "/org/pacey/dropwizardpebble/the-best-view-ever"
    }

}
