package com.youness.mowerkata;

import com.youness.mowerkata.domain.Direction;
import com.youness.mowerkata.domain.GrassLand;
import com.youness.mowerkata.domain.Mower;
import com.youness.mowerkata.exception.InvalidLandDimensionException;
import com.youness.mowerkata.exception.OutOfBoundsPositionException;
import org.junit.Before;
import org.junit.Test;

public class MowerTests {
    private static GrassLand GRASS_LAND;

    @Before
    public void setup() throws InvalidLandDimensionException {
        GRASS_LAND = new GrassLand(5, 5);
    }


    @Test(expected = OutOfBoundsPositionException.class)
    public void should_throw_exception_when_mower_out_of_bound() throws Exception {
        new Mower(GRASS_LAND, 0, 6, Direction.NORTH);
    }

}
