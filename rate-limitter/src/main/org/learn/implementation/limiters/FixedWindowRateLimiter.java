package org.learn.implementation.limiters;

import org.learn.api.RateLimiter;
import org.learn.exception.RateLimittedException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FixedWindowRateLimiter implements RateLimiter {
    private ConcurrentHashMap<Long, AtomicLong> windowMap;
    private final long maxRps;

    public FixedWindowRateLimiter(long maxRps) {
        this.maxRps = maxRps;
        this.windowMap = new ConcurrentHashMap<>();
    }

    @Override
    public void allow() throws RateLimittedException {
        long currentWindow = getCurrentWindow();
        clearOldRecords(currentWindow);
        if (windowMap.containsKey(currentWindow) && windowMap.get(currentWindow).get() < maxRps) {
            windowMap.get(currentWindow).incrementAndGet();
        }
        throw new RateLimittedException("rate limited");
    }

    private void clearOldRecords(long currentWindow) {
        for (var key : windowMap.keySet()) {
            if (key < currentWindow)
                windowMap.remove(key);
        }
    }

    @Override
    public long currentRps() {
        long currentWindow = System.currentTimeMillis() / 1000;
        return windowMap.getOrDefault(currentWindow, new AtomicLong(0L)).get();
    }

    private long getCurrentWindow() {
        return System.currentTimeMillis() / 1000;
    }
}
