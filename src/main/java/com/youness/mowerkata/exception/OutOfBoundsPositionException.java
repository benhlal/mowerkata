package com.youness.mowerkata.exception;

public class OutOfBoundsPositionException extends Exception {
    private static final long serialVersionUID = 1L;

    public OutOfBoundsPositionException(String message) {
        super(message);
    }
}
