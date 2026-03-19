package net.study.tdd.common.learning.thread.concurrent

import spock.lang.Specification
import net.study.tdd.common.learning.thread.BlockingTestSupport

class BlackMarketSpec extends Specification {

    def "스레드 블록 테스트"() {
        given:
        BlackMarket market = new BlackMarket();

        expect:
        BlockingTestSupport.assertBlocks({
            market.buyTicket()
        })
    }

    class BlackMarket {

        void buyTicket() {
            Thread.currentThread().wait()
        }
    }
}
