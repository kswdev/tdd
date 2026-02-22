package net.study.tdd

import net.study.tdd.template.Template
import net.study.tdd.template.error.MissingValueException
import spock.lang.Specification

class TestTemplateSpec extends Specification {

    private Template template

    def setup() {
        template = new Template('${one}, ${two}, ${three}');
        template.set("one", "1");
        template.set("two", "2");
        template.set("three", "3");
    }

    def multipleVariables() {

        when:
        def result = template.evaluate()

        then:
        result == "1, 2, 3"
    }

    def unknownVariablesAreIgnored() {

        when:
        template.set("doesnotexist", "whatever")
        def result = template.evaluate()

        then:
        result == "1, 2, 3"
    }

    def missingValueRaisesException() {

        given:
        def template = new Template('${foo}')
        template.set("one", "1")

        when:
        template.evaluate()

        then:
        def e = thrown(MissingValueException.class)
        e.getMessage() == 'No value for ${foo}'
    }
}
