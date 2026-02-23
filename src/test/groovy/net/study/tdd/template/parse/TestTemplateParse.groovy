package net.study.tdd.template.parse

import spock.lang.Specification

class TestTemplateParse extends Specification {

    def templateRendering() {
        given:
        TemplateParse parse = new TemplateParse();

        when:
        List<String> segments = parse.parse(template);

        then:
        segments == expectSegments

        where:
        scenario                    | template             | expectSegments
        'empty template'            | ''                   | List.of('')
        'plain text only'           | 'plain text only'    | List.of('plain text only')
        'parsing multiple variable' | '${a} : ${b} : ${c}' | List.of('${a}', ' : ', '${b}', ' : ', '${c}')
    }
}
