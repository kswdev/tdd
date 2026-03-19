package net.study.tdd.common.learning.thread.longlasting

import spock.lang.Specification
import spock.util.concurrent.PollingConditions

class LongLastingAsynchronousSpec extends Specification {

    def "시간이 오래걸리는 비동기 테스트"() {
        given:
        def calc = new LongLastingCalculation()
        def conditions = new PollingConditions(timeout: 2)

        when:
        calc.start()

        then:
        conditions.eventually {
            assert calc.getResult() == 42
        }
    }

    class LongLastingCalculation {

        private volatile int result = 0

        void start() {
            new Thread(() -> {
                try {
                    Thread.sleep(1000) // 오래 걸리는 작업 흉내
                    result = 42
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt()
                }
            }).start()
        }

        int getResult() {
            return result
        }
    }
}
