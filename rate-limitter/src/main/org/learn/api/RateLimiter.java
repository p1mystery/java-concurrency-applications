package org.learn.api;

import org.learn.exception.RateLimittedException;

public interface RateLimiter {
    void allow() throws RateLimittedException, InterruptedException;

    long currentRps();
}