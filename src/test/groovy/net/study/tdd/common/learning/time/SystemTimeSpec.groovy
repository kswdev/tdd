package net.study.tdd.common.learning.time

import net.study.tdd.common.time.SystemTime
import net.study.tdd.common.time.TimeSource
import spock.lang.Specification

class SystemTimeSpec extends Specification {

    def setup() {
        SystemTime.reset()
    }

    def "시스템 시간을 호출하면 fake 시간을 반환한다."() {
        given:
        final long fakeTime = 123456790L
        SystemTime.setTimeSource(new TimeSource() {
            long millis() {
                return fakeTime
            }
        })

        when:
        long clock = SystemTime.asMillis()

        then:
        clock == fakeTime
    }
}
