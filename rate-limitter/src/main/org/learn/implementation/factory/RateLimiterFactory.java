package org.learn.implementation.factory;

import lombok.NoArgsConstructor;
import org.learn.api.RateLimiter;
import org.learn.constant.LimiterType;
import org.learn.implementation.limiters.FixedWindowRateLimiter;
import org.learn.implementation.limiters.SlidingWindowRateLimiter;
import org.learn.implementation.limiters.TokenBucketRateLimiter;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor
public class RateLimiterFactory {
    public RateLimiter getLimiterImpl(LimiterType type, Object config) {
        switch (type) {
            case LimiterType.SLIDING_WINDOW -> {
                return new SlidingWindowRateLimiter(1);
            }
            case LimiterType.LEAKY_BUCKET -> {
                return new TokenBucketRateLimiter(10, 1000, TimeUnit.MILLISECONDS);
            }
            default -> {
                return new FixedWindowRateLimiter(1);
            }
        }
    }
}
