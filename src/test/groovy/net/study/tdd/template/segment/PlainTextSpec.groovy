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
}
