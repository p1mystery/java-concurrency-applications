package org.learn.exception;

public class RateLimittedException extends Exception {
    public RateLimittedException(String message) {
        super(message);
    }

    public RateLimittedException(String message, Throwable cause) {
        super(message, cause);
    }
}
