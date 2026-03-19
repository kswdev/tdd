package net.study.tdd.common.learning.thread

import java.util.concurrent.CyclicBarrier

class SynchedThread extends Thread {

    private final CyclicBarrier entryBarrier
    private final CyclicBarrier exitBarrier

    SynchedThread(
            Runnable runnable,
            CyclicBarrier entryBarrier,
            CyclicBarrier exitBarrier
    ) {
        super(runnable);
        this.entryBarrier = entryBarrier
        this.exitBarrier = exitBarrier
    }

    void run() {
        try {
            entryBarrier.await()
            super.run()
            exitBarrier.await()
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
