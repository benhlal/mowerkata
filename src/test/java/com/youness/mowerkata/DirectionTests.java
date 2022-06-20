package com.youness.mowerkata;

import com.youness.mowerkata.domain.Direction;
import com.youness.mowerkata.exception.InvalidDirectionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTests {

    @Test
    public void when_code_is_E_should_return_east() throws Exception {
        assertEquals(Direction.EAST, Direction.getDirectionByCode("E"));
    }

    @Test
    public void when_code_is_S_should_return_south() throws Exception {
        assertEquals(Direction.SOUTH, Direction.getDirectionByCode("S"));
    }

    @Test(expected = InvalidDirectionException.class)
    public void should_throw_exception_when_direction_not_valid() throws Exception {
        Direction.getDirectionByCode("X");
    }

    // 360 LEFT
    @Test
    public void when_direction_NORTH_direction_after_rotateLeft_new_direction_should_be_WEST() throws Exception {
        assertEquals(Direction.WEST, Direction.NORTH.rotateLeft());
    }

    @Test
    public void when_direction_WEST_direction_after_rotateLeft_new_direction_should_be_SOUTH() throws Exception {
        assertEquals(Direction.SOUTH, Direction.WEST.rotateLeft());
    }

    @Test
    public void when_direction_SOUTH_direction_after_rotateLeft_new_direction_should_be_EAST() throws Exception {
        assertEquals(Direction.EAST, Direction.SOUTH.rotateLeft());
    }

    @Test
    public void when_direction_EAST_direction_after_rotateLeft_new_direction_should_be_NORTH() throws Exception {
        assertEquals(Direction.NORTH, Direction.EAST.rotateLeft());
    }


    // now turn 360 at once right

    @Test
    public void when_start_direction_NORTH_after_rotateRight_360_new_direction_should_be_NORTH() throws Exception {

        Direction after360 = Direction.NORTH
                .rotateRight() // EAST
                .rotateRight()//SOUTH
                .rotateRight()//WEST
                .rotateRight();// NORTH
        assertEquals(Direction.NORTH, after360);
    }
}
