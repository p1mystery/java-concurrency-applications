package org.learn.implementation.factory;

import lombok.NoArgsConstructor;
import org.learn.api.RateLimiter;
import org.learn.constant.LimiterType;
import org.learn.implementation.limiters.FixedWindowRateLimiter;
import org.learn.implementation.limiters.LeakyBucketRateLimiter;
import org.learn.implementation.limiters.SlidingWindowRateLimiter;

@NoArgsConstructor
public class RateLimiterFactory {
    public RateLimiter getLimiterImpl(LimiterType type, Object config) {
        switch (type) {
            case LimiterType.SLIDING_WINDOW -> {
                return new SlidingWindowRateLimiter(1);
            }
            case LimiterType.LEAKY_BUCKET -> {
                return new LeakyBucketRateLimiter();
            }
            default -> {
                return new FixedWindowRateLimiter(1);
            }
        }
    }
}
