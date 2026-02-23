package net.study.tdd.template.segment

import spock.lang.Specification

class PlainTextSpec extends Specification {

    def plainTextEvaluatesAsIs() {
        given:
        Map<String, String> variables = new HashMap<String, String>()
        def plainText = new PlainText(template)

        expect:
        plainText.evaluate(variables) == expectResult

        where:
        scenario       | template  | expectResult
        'plain text'   | 'acc def' | 'acc def'
    }

    def variableEvaluatesToItsValue() {
        given:
        Map<String, String> variables = new HashMap<String, String>()
        variables.put(name, value)
        def variable = new Variable(name)

        expect:
        variable.evaluate(variables) == value

        where:
        scenario            | name    | value
        'plain text only'   | 'myvar' | 'myvalue'
    }
}
