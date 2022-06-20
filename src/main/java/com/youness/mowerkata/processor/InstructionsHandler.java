package com.youness.mowerkata.processor;

import com.youness.mowerkata.command.*;
import com.youness.mowerkata.domain.Direction;
import com.youness.mowerkata.domain.GrassLand;
import com.youness.mowerkata.domain.Mower;
import com.youness.mowerkata.exception.*;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;

import static com.youness.mowerkata.util.Validators.*;
import static java.lang.Integer.parseInt;

@Slf4j
public class InstructionsHandler {

    private static final int FIRST_ELEMENT_IDX_0 = 0;
    private List<String> fileLines;

    public InstructionsHandler(List<String> fileLines) {
        this.fileLines = fileLines;
    }

    public GrassLand handleFileInstructions() throws InstructionsProcessingException, CommandsProcessingException {

        // check file lines count (Land, Mower,Commands)
        isValidInstructionFile(fileLines);
        // take first line for land definition instruction
        String landDefinitionLine = fileLines.remove(FIRST_ELEMENT_IDX_0);

        GrassLand targetGrassLand = initGrassLand(landDefinitionLine);

        // Mowers instructions  [1 2 N, GAGAGAGAA, 3 3 E, AADAADADDA ]


        // Iterator i = fileLines.listIterator();


        while (!fileLines.isEmpty()) {
            IcommandableDevice mower = null;
            try {
                mower = initMower(fileLines.remove(FIRST_ELEMENT_IDX_0), targetGrassLand);
            } catch (InstructionsProcessingException e) {
                throw new RuntimeException(e);
            }
            try {
                if (fileLines.size() == 0) throw new InstructionsProcessingException("Incomplete instruction file");
                processMowerInputCommand(fileLines.remove(FIRST_ELEMENT_IDX_0), mower);
            } catch (CommandsProcessingException e) {
                throw new RuntimeException(e);
            }

        }

        /*     fileLines.stream().forEach(line -> {
            IcommandableDevice mower = null;
            try {
                mower = initMower(line, targetGrassLand);
            } catch (InstructionsProcessingException e) {
                throw new RuntimeException(e);
            }
            try {
                processMowerInputCommand(fileLines.get(fileLines.indexOf(line) + 1), mower);
            } catch (CommandsProcessingException e) {
                throw new RuntimeException(e);
            }

        });*/


        return targetGrassLand;
    }


    public GrassLand initGrassLand(String inputLine) throws InstructionsProcessingException {
        String[] dims = inputLine.split(SPACE_SEPARATOR);
        if (!isValidGrassDefinition.test(inputLine)) {
            throw new InstructionsProcessingException("invalid land dims format");
        } else {
            int max_x = parseInt(dims[0]);
            int max_y = parseInt(dims[1]);

            try {
                return new GrassLand(max_x, max_y);
            } catch (InvalidLandDimensionException e) {
                throw new InstructionsProcessingException("invalid land dimension");
            }
        }
    }

    public IcommandableDevice initMower(String inputLine, GrassLand targetGrassLand) throws InstructionsProcessingException {

        String[] mowerPos = inputLine.split(SPACE_SEPARATOR);
        if (mowerPos.length != 3) throw new InstructionsProcessingException("incomplete mower position definition");

        if (isValidMowerPositionDefinition.test(inputLine)) {
            int x = parseInt(mowerPos[0]);
            int y = parseInt(mowerPos[1]);
            Direction direction;
            try {
                direction = Direction.getDirectionByCode(mowerPos[2]);
            } catch (InvalidDirectionException e) {
                log.error("init mower error");
                throw new InstructionsProcessingException("invalid mower direction");
            }

            try {

                // affecting mover to land in position and direction
                Mower mower = new Mower(targetGrassLand, x, y, direction);
                //adding mower to mowers Queue on grass land
                targetGrassLand.addToMowersQueueOnGrassLand(mower);
                return mower;
            } catch (OutOfBoundsPositionException e) {
                throw new InstructionsProcessingException("invalid mower position");
            }
        } else {
            throw new InstructionsProcessingException("invalid mower format");
        }
    }

    public Queue<ICommand> processMowerInputCommand(String inputLine, IcommandableDevice commandableMower) throws CommandsProcessingException {
        Queue<ICommand> commandQueue = new LinkedList<>();
        if (isValidMowerCommandDefinition.test(inputLine)) {
            if (isValidMowerCommandDefinition.test(inputLine)) {
                inputLine.chars().mapToObj(c -> (char) c).forEach(inputCommand -> {
                    ICommand mowerCmdInstance = getMowerCommandInstance(Character.toString(inputCommand));
                    commandableMower.addToCommandQueue(mowerCmdInstance);
                    // for testing purposes
                    commandQueue.add(mowerCmdInstance);
                });
            }

        } else {
            throw new CommandsProcessingException("invalid mower action format");
        }
        return commandQueue;
    }

    public ICommand getMowerCommandInstance(String prefix) {


        Function<String, ICommand> commandInstanceProvider = commandAction -> {
            switch (commandAction) {
                case "A":
                    return new AdvanceCommand();
                case "D":
                    return new TurnRightCommand();
                case "G":
                    return new TurnLeftCommand();
                default:
                    return new PauseCommand();
            }
        };
        return commandInstanceProvider.apply(prefix);

    }


}
