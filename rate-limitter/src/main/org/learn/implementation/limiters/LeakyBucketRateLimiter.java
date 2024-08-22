package org.learn.implementation.limiters;

import lombok.AllArgsConstructor;
import org.learn.api.RateLimiter;

@AllArgsConstructor
public class LeakyBucketRateLimiter implements RateLimiter {
    @Override
    public void allow() {

    }

    @Override
    public long currentRps() {
        return 0;
    }
}
