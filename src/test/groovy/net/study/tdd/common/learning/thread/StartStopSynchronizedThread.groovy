package net.study.tdd.common.learning.thread

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class StartStopSynchronizedThread extends Thread {
    private CountDownLatch threadStarted
    private CountDownLatch threadStopped

    StartStopSynchronizedThread(Runnable task) {
        super(task)
        threadStarted = new CountDownLatch(1)
        threadStopped = new CountDownLatch(1)
    }

    @Override
    void run() {
        threadStarted.countDown()
        super.run()
        threadStopped.countDown()
    }

    boolean waitForStarted(long timeout, TimeUnit unit) throws InterruptedException {
        if (threadStarted.await(timeout, unit)) {
            return true
        }
        println("Thread not started within timeout.")
        return false
    }

    boolean waitForStopped(int timeout, TimeUnit unit) throws InterruptedException {
        if (threadStopped.await(timeout, unit)) {
            return true
        }
        println("Thread not stopped within timeout.")
        return false
    }
}