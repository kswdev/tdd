package net.study.tdd

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = TddApplication)
class TddApplicationTest  extends Specification {

    void contextLoads() {
        expect:
        true
    }
}
