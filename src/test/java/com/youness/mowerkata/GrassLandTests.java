package com.youness.mowerkata;

import com.youness.mowerkata.domain.Direction;
import com.youness.mowerkata.domain.GrassLand;
import com.youness.mowerkata.domain.Mower;
import com.youness.mowerkata.exception.IllegalMowerPositionOnGrassLand;
import com.youness.mowerkata.exception.InvalidLandDimensionException;
import com.youness.mowerkata.exception.OutOfBoundsPositionException;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GrassLandTests {


    @Test(expected = InvalidLandDimensionException.class)
    public void when_dimension_is_negative_it_should_throw_exception() throws InvalidLandDimensionException {
        new GrassLand(-2, 4);
    }

    @Test
    public void when_create_mowers_we_attach_them_to_grass_land() throws InvalidLandDimensionException, OutOfBoundsPositionException {

        GrassLand square = new GrassLand(5, 5);
        square.addToMowersQueueOnGrassLand(new Mower(square, 1, 1, Direction.SOUTH));
        square.addToMowersQueueOnGrassLand(new Mower(square, 1, 1, Direction.SOUTH));
        assertEquals(square.availableMowersCount(), 2);
    }

    @Test(expected = IllegalMowerPositionOnGrassLand.class)
    @Ignore
    public void when_create_mowers_we_attach_them_to_grass_land_to_the_same_location() throws IllegalMowerPositionOnGrassLand, InvalidLandDimensionException, OutOfBoundsPositionException {

        GrassLand square = new GrassLand(5, 5);
        square.addToMowersQueueOnGrassLand(new Mower(square, 1, 1, Direction.SOUTH));
        square.addToMowersQueueOnGrassLand(new Mower(square, 1, 1, Direction.SOUTH));
        assertEquals(square.availableMowersCount(), 2);
    }
}
