package org.learn.implementation.limiters;

import lombok.AllArgsConstructor;
import org.learn.api.RateLimiter;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@AllArgsConstructor
public class SlidingWindowRateLimiter implements RateLimiter {
    private long maxRps;
    private Deque<Long> windowQueue;
    private ReentrantLock lock;
    private Condition available;

    public SlidingWindowRateLimiter(long maxRps) {
        this.maxRps = maxRps;
        this.windowQueue = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.available = lock.newCondition();
    }

    @Override
    public void allow() throws InterruptedException {
        try {
            lock.lock();
            long currentWindow = getCurrentWindow();
            clearOldRecords(currentWindow);
            while (windowQueue.size() >= maxRps) {
                available.await();
            }
            windowQueue.addFirst(currentWindow);
        } finally {
            lock.unlock();
        }
    }

    private void clearOldRecords(long currentWindow) {
        while (!windowQueue.isEmpty() && windowQueue.peekLast() < currentWindow - 1000) {
            windowQueue.pollLast();
        }
        if (windowQueue.size() < maxRps)
            available.signal();
    }

    @Override
    public long currentRps() {
        return 0;
    }

    private long getCurrentWindow() {
        return System.currentTimeMillis();
    }
}
