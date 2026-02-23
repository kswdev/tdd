package net.study.tdd.learning.regex;

import spock.lang.Specification

import java.util.regex.Matcher
import java.util.regex.Pattern

class RegexLearningTest extends Specification {

    def testFindStartAndEnd() {

        given:
        String haystack = "The needle shop sells needles"
        String regex = "(needle)"
        Matcher matcher = Pattern.compile(regex).matcher(haystack)

        when:
        def matches = []
        while (matcher.find()) {
            matches << [matcher.start(), matcher.end()]
        }

        then:
        matches == [[4, 10],
                    [22, 28]]
    }
}
