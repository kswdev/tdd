package net.study.tdd.template


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

        given:
        template.set("doesnotexist", "whatever")

        when:
        def result = template.evaluate()

        then:
        result == "1, 2, 3"
    }

    def variablesGetProcessedJustOnce() throws Exception {

        given:
        template.set("one", '${one}');
        template.set("two", '${three}');
        template.set("three", '${two}');

        when:
        def result = template.evaluate()

        then:
        result == '${one}, ${three}, ${two}'
    }
}
