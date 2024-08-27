package org.learn.implementation.limiters;

import lombok.AllArgsConstructor;
import org.learn.api.RateLimiter;
import org.learn.exception.RateLimittedException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@AllArgsConstructor
public class TokenBucketRateLimiter implements RateLimiter {
    private final long maxTokens;
    private final long refillInterval;
    private final AtomicLong availableTokens;
    private long nextRefillTime;

    public TokenBucketRateLimiter(long maxTokens, long refillInterval, TimeUnit unit) {
        this.maxTokens = maxTokens;
        this.refillInterval = unit.toMillis(refillInterval);
        this.availableTokens = new AtomicLong(maxTokens);
        this.nextRefillTime = System.currentTimeMillis() + this.refillInterval;
    }

    private synchronized void refillTokensIfNeeded() {
        long now = System.currentTimeMillis();
        if (now >= nextRefillTime) {
            availableTokens.set(maxTokens);
            nextRefillTime = now + refillInterval;
        }
    }

    @Override
    public void allow() throws RateLimittedException {
        refillTokensIfNeeded();
        if (availableTokens.get() > 0) {
            availableTokens.decrementAndGet();
        }
        throw new RateLimittedException("rate limited");
    }

    @Override
    public long currentRps() {
        return this.availableTokens.get();
    }
}

