package com.youness.mowerkata.util;

import com.youness.mowerkata.exception.InvalidLandDimensionException;

public abstract class Validators {

    public static void checkDimension(int width, int height) throws InvalidLandDimensionException {
        if (width < 0 || height < 0) {
            throw new InvalidLandDimensionException("Land dimensions must be positive");
        }
    }
}
