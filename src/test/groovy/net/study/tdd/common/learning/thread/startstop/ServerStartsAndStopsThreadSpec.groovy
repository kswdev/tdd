package net.study.tdd.common.learning.thread.startstop

import net.study.tdd.common.learning.thread.StartStopSynchronizedThread
import spock.lang.Specification

import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit

class ServerStartsAndStopsThreadSpec extends Specification {

    private StartStopSynchronizedThread thread

    def "스레드를 시작하고 중지하는 서버 테스트"() {
        given:
        ThreadFactory threadFactory = { Runnable task ->
            thread = new StartStopSynchronizedThread(task)
        }

        Server server = new Server()
        server.setThreadFactory(threadFactory)

        when:
        server.start()

        then:
        server.running
        thread.waitForStarted(1, TimeUnit.SECONDS)

        when:
        server.stop()

        then:
        !server.running
        thread.waitForStopped(1, TimeUnit.SECONDS)
    }

    class Server {
        private ThreadFactory threadFactory = Thread::new
        private Thread thread
        private volatile boolean running = false

        void setThreadFactory(ThreadFactory threadFactory) {
            this.threadFactory = threadFactory
        }

        void start() {
            running = true

            thread = threadFactory.newThread(() -> {
                try {
                    while (running) {
                        // 서버 작업 (예: 요청 처리)
                        Thread.sleep(2000) // 단순 예시
                    }
                } catch (InterruptedException e) {
                    // 종료 신호로 간주
                }
            });

            thread.start()
        }

        void stop() {
            running = false

            if (thread != null) {
                thread.interrupt() // blocking 상태 깨우기
            }
        }
    }
}
