package net.study.tdd.template.segment

import net.study.tdd.template.error.MissingValueException
import spock.lang.Specification

class VariableSpec extends Specification {

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

    def missingValueRaisesException() {

        given:
        Map<String, String> variables = new HashMap<String, String>()
        variables.put(key, value)
        def variable = new Variable(notExistName)

        when:
        variable.evaluate(variables)

        then:
        def e = thrown(MissingValueException.class)
        e.getMessage() == "No value for ${notExistName}"

        where:
        scenario        | key     | value     | notExistName
        'missing value' | 'myvar' | 'myvalue' | 'notExist'
    }
}
