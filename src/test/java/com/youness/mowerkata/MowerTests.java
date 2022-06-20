package com.youness.mowerkata;

import com.youness.mowerkata.command.AdvanceCommand;
import com.youness.mowerkata.command.ICommand;
import com.youness.mowerkata.command.TurnLeftCommand;
import com.youness.mowerkata.command.TurnRightCommand;
import com.youness.mowerkata.domain.Direction;
import com.youness.mowerkata.domain.GrassLand;
import com.youness.mowerkata.domain.Mower;
import com.youness.mowerkata.exception.InvalidLandDimensionException;
import com.youness.mowerkata.exception.OutOfBoundsPositionException;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class MowerTests {
    private static GrassLand GRASS_LAND;
    private Mower mower;

    @Before
    public void setup() throws InvalidLandDimensionException {
        GRASS_LAND = new GrassLand(5, 5);
        try {
            mower = new Mower(GRASS_LAND, 1, 1, Direction.NORTH);
        } catch (OutOfBoundsPositionException e) {
            throw new RuntimeException(e);
        }
    }


    @Test(expected = OutOfBoundsPositionException.class)
    public void should_throw_exception_when_mower_out_of_bound() throws Exception {
        new Mower(GRASS_LAND, 0, 6, Direction.NORTH);
    }


    @Test
    public void given_coordinates_X1_Y1_when_run_start_it_should_execute_one_command_Advance() {

        ICommand command = new AdvanceCommand();
        mower.getCommandQueue().add(command);
        mower.start();
        assertEquals(1, mower.getPosition().getCoordinates().getX());
        assertEquals(2, mower.getPosition().getCoordinates().getY());
    }

    @Test
    public void given_North_when_run_start_it_should_execute_one_command_TurnLeft() {

        ICommand command = new TurnLeftCommand();
        mower.getCommandQueue().add(command);
        mower.start();
        assertEquals(Direction.WEST, mower.getPosition().getDirection());

    }

    @Test
    public void given_North_when_run_start_it_should_execute_one_command_TurnRight() {
        ICommand command = new TurnRightCommand();
        mower.getCommandQueue().add(command);
        mower.start();
        assertEquals(Direction.EAST, mower.getPosition().getDirection());
    }


    @Test
    public void given_North_when_run_startAll_it_should_execute_all_commands_and_zizag() {
// statring cordinates 1,1
        //  ADADAGAGA = zigzag
        Queue<ICommand> drawSquareCommand = new LinkedList<>();
        drawSquareCommand.add(new AdvanceCommand());
        drawSquareCommand.add(new TurnRightCommand());
        drawSquareCommand.add(new AdvanceCommand());
        drawSquareCommand.add(new TurnRightCommand());
        drawSquareCommand.add(new AdvanceCommand());
        drawSquareCommand.add(new TurnLeftCommand());
        drawSquareCommand.add(new AdvanceCommand());
        drawSquareCommand.add(new TurnLeftCommand());
        drawSquareCommand.add(new AdvanceCommand());

        mower.setCommandQueue(drawSquareCommand);

        String res = mower.startAll();
        System.out.println(res);

        // direction should be NORTH
        assertEquals(Direction.NORTH, mower.getPosition().getDirection());
        // new coordinates should be  3,2 position 3 2 N
        assertEquals(3, mower.getPosition().getCoordinates().getX());
        assertEquals(2, mower.getPosition().getCoordinates().getY());

    }


}
