package net.study.tdd

import net.study.tdd.template.Template
import spock.lang.Specification

class TestTemplatePerformance extends Specification {

    private Template template

    def setup() {
        def text = new StringBuilder()

        // 100 words 만들기
        for (int i = 0; i < 100; i++) {

            // 20개는 변수로 치환
            if (i < 20) {
                text.append('${var').append(i).append('} ')
            } else {
                text.append('word').append(i).append(' ')
            }
        }

        template = new Template(text.toString())

        // 15자 정도 되는 값 세팅
        for (int i = 0; i < 20; i++) {
            template.set("var$i", "123456789012345")
        }
    }

    def templateWith100WordsAnd20Variables() {
        given:
        long expected = 200L;

        when:
        long time = System.currentTimeMillis();
        time = System.currentTimeMillis() - time;

        then:
        time <= expected
    }
}
