package com.youness.mowerkata;

import com.youness.mowerkata.command.*;
import com.youness.mowerkata.domain.Direction;
import com.youness.mowerkata.domain.GrassLand;
import com.youness.mowerkata.domain.Mower;
import com.youness.mowerkata.domain.Position;
import com.youness.mowerkata.exception.CommandsProcessingException;
import com.youness.mowerkata.exception.InstructionsProcessingException;
import com.youness.mowerkata.processor.InstructionsHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InstructionHandlerTests {
    private InstructionsHandler instructionsHandler;

    @Before
    public void setup() {
        instructionsHandler = new InstructionsHandler(new ArrayList<>());
    }

    @Test
    public void when_valid_land_dims_definition_should_create_land() {
        GrassLand grassLand = null;
        try {
            grassLand = instructionsHandler.initGrassLand("5 9");
        } catch (InstructionsProcessingException e) {
            throw new RuntimeException(e);
        }
        assertEquals(5, grassLand.getWidth());
        assertEquals(9, grassLand.getHeight());
    }

    @Test(expected = InstructionsProcessingException.class)
    public void when_invalid_land_definition_should_throw_exception() throws InstructionsProcessingException {
        GrassLand grassLand = instructionsHandler.initGrassLand("595");
        assertEquals(5, grassLand.getWidth());
        assertEquals(9, grassLand.getHeight());
    }


    @Test
    public void when_valid_command_input_should_append_command_to_queue() throws Exception {
        IcommandableDevice mower = new Mower(new GrassLand(5, 5), 3, 1, Direction.EAST);
        Queue<ICommand> queue = instructionsHandler.processMowerInputCommand("A", mower);
        assertEquals(1, queue.size());
    }

    @Test
    public void when_valid_command_input_should_return_corresponding_command_instance_A() throws Exception {
        IcommandableDevice mower = new Mower(new GrassLand(5, 5), 3, 1, Direction.EAST);
        Queue<ICommand> queue = instructionsHandler.processMowerInputCommand("A", mower);
        assertTrue(queue.peek() instanceof AdvanceCommand);
    }

    @Test
    public void when_valid_command_input_should_return_corresponding_command_instance_D() throws Exception {
        IcommandableDevice mower = new Mower(new GrassLand(5, 5), 3, 1, Direction.EAST);
        Queue<ICommand> queue = instructionsHandler.processMowerInputCommand("D", mower);
        assertTrue(queue.peek() instanceof TurnRightCommand);
    }

    @Test
    public void when_valid_command_input_should_return_corresponding_command_instance_G() throws Exception {
        IcommandableDevice mower = new Mower(new GrassLand(5, 5), 3, 1, Direction.EAST);
        Queue<ICommand> queue = instructionsHandler.processMowerInputCommand("G", mower);
        assertTrue(queue.peek() instanceof TurnLeftCommand);
    }


    @Test(expected = InstructionsProcessingException.class)
    public void when_definition_incomplete_should_throw_exception() throws Exception {
        List<String> fileLine = new ArrayList<>();
        fileLine.add("ADDDADAG");
        fileLine.add("5 5");
        InstructionsHandler instructionsHandler = new InstructionsHandler(fileLine);
        instructionsHandler.handleFileInstructions();
    }

    @Test
    public void full_fil_test() throws InstructionsProcessingException, CommandsProcessingException {
        List<String> fileLine = new ArrayList<>();
        fileLine.add("5 5");
        fileLine.add("1 2 N");
        fileLine.add("GAGAGAGAA");
        fileLine.add("3 3 E");
        fileLine.add("AADAADADDA");
        InstructionsHandler instructionsHandler = new InstructionsHandler(fileLine);
        GrassLand grassLand = instructionsHandler.handleFileInstructions();
        Map<Position, String> positionStringMap = grassLand.invokeStartAll();
        assertTrue(!positionStringMap.isEmpty());
        assertEquals(2, positionStringMap.size());
        assertTrue(positionStringMap.values().stream().anyMatch("1 3 N"::equals));
        assertTrue(positionStringMap.values().stream().anyMatch("5 1 E"::equals));
    }


}
