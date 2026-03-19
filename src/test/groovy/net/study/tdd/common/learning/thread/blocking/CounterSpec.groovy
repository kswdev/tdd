package net.study.tdd.common.learning.thread.blocking

import net.study.tdd.common.learning.thread.SynchedThread
import spock.lang.Specification

import java.util.concurrent.CyclicBarrier

class CounterSpec extends Specification {

    private Counter counter

    def setup() {
        counter = new Counter()
    }

    def "기본 카운터 기능 테스트"() {
        expect:
        counter.value() == 0

        when:
        counter.increment()

        then:
        counter.value() == 1

        when:
        counter.increment()

        then:
        counter.value() == 2
    }

    def "동시성 스레드 실패 테스트"() {
        given:
        final int numberOfThreads = 20
        final int incrementsPerThread = 1000

        CyclicBarrier entryBarrier = new CyclicBarrier(numberOfThreads+1)
        CyclicBarrier exitBarrier = new CyclicBarrier(numberOfThreads+1)

        Runnable runnable = {
            for (int i = 0; i < incrementsPerThread; i++) {
                counter.increment()
            }
        }

        when:
        for (int i = 0; i < numberOfThreads; i++) {
            new SynchedThread(runnable, entryBarrier, exitBarrier).start()
        }

        then:
        counter.value() == 0

        when:
        entryBarrier.await()
        exitBarrier.await()

        then:
        numberOfThreads * incrementsPerThread != counter.value()
    }

    class Counter {
        private int counter

        void increment() {
            counter++
        }

        int value() {
            return counter
        }
    }
}
