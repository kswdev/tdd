package net.study.tdd

import net.study.tdd.template.Template
import spock.lang.Specification

class TestTemplateSpec extends Specification {

    def oneVariable() {
        given:
        String name = "Reader"
        Template template = new Template('Hello, ${name}')

        when:
        template.set("name", name)

        then:
        "Hello, Reader" == template.evaluate()
    }

    def differentValue() {
        given:
        String name = "Someone else"
        Template template = new Template('Hi, ${name}')

        when:
        template.set("name", name)

        then:
        "Hi, Someone else" == template.evaluate()
    }

    def multipleVariables() {
        given:
        String one = "1"
        String two = "2"
        String three = "3"
        Template template = new Template('${one}, ${two}, ${three}')

        when:
        template.set("one", one)
        template.set("two", two)
        template.set("three", three)

        then:
        "1, 2, 3" == template.evaluate()
    }

    def unknownVariablesAreIgnored() {
        given:
        String name = "Reader"
        Template template = new Template('Hello, ${name}')

        when:
        template.set("name", name)
        template.set("doesnotexist", "Hi");

        then:
        "Hello, Reader" == template.evaluate()
    }
}
