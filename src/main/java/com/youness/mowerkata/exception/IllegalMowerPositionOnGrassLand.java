package com.youness.mowerkata.exception;

public class IllegalMowerPositionOnGrassLand extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IllegalMowerPositionOnGrassLand(String message) {
        super(message);
    }
}


