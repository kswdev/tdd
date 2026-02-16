package net.study.tdd

import net.study.tdd.template.Template
import spock.lang.Specification

class TestTemplateSpec extends Specification {

    def oneVariable() {
        given:
        String name = Reader
        Template template = new Template('Hello, ${name}');

        when:
        template.set("name", name);

        then:
        "Hello, Reader" == template.evaluate();
    }
}
