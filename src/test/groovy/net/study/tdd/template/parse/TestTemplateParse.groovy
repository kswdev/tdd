package net.study.tdd.template.parse

import net.study.tdd.template.segment.PlainText
import net.study.tdd.template.segment.Segment
import net.study.tdd.template.segment.Variable
import spock.lang.Specification

class TestTemplateParse extends Specification {

    def parsingTemplateIntoStringList() {

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

    def parsingTemplateIntoSegmentList() {

        given:
        TemplateParse parse = new TemplateParse();

        when:
        List<Segment> segments = parse.parseSegments(template)

        then:
        segments == expectSegments

        where:
        scenario                    | template             | expectSegments
        'empty template'            | ''                   | List.of(new PlainText(''))
        'plain text only'           | 'plain text only'    | List.of(new PlainText('plain text only'))
        'parsing multiple variable' | '${a} : ${b} : ${c}' | List.of(new Variable('a'), new PlainText(' : '), new Variable('b'), new PlainText(' : '), new Variable('c'))
    }
}
