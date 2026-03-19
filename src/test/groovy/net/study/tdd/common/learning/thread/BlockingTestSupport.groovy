package net.study.tdd.common.learning.thread

import java.util.concurrent.atomic.AtomicBoolean

class BlockingTestSupport {

    def static assertBlocks(Runnable blockingCall) throws Exception {
        AtomicBoolean completed = new AtomicBoolean(false)

        Thread t = new Thread(() -> {
            try {
                blockingCall.run()
                completed.set(true) // block 안 됨
            } catch (Exception ignored) {}
        });

        t.start()

        Thread.sleep(500) // 충분히 block 되게

        t.interrupt()
        t.join(1000)

        if (t.isAlive()) {
            throw new AssertionError("Thread didn't terminate!")
        }

        if (completed.get()) {
            throw new AssertionError("Method didn't block!")
        }

        return true
    }
}
