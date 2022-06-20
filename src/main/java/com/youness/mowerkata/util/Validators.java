package com.youness.mowerkata.util;

import com.youness.mowerkata.exception.InstructionsProcessingException;
import com.youness.mowerkata.exception.InvalidLandDimensionException;

import java.util.List;
import java.util.function.Predicate;

public abstract class Validators {
    private static final String REGEX_GRASS_LAND_DIM = "^([0-9]+) ([0-9]+)$";

    private static final String REGEX_MOWER_INI_POS = "^([0-9]+) ([0-9]+) ([NESW])$";

    private static final String REGEX_MOWER_INPUT_CMD = "^([ADG]*)$";
    public static final String SPACE_SEPARATOR = " ";


    public static Predicate<String> isValidGrassDefinition = (String input) -> input.matches(REGEX_GRASS_LAND_DIM);
    public static Predicate<String> isValidMowerPositionDefinition = (String input) -> input.matches(REGEX_MOWER_INI_POS);
    public static Predicate<String> isValidMowerCommandDefinition = (String input) -> input.matches(REGEX_MOWER_INPUT_CMD);

    public static void isValidInstructionFile(List<String> fileLines) throws InstructionsProcessingException {
        if (fileLines.size() % 3 == 0) {
            throw new InstructionsProcessingException("mower definition incomplete");
        }
    }

    public static void checkDimension(int width, int height) throws InvalidLandDimensionException {
        if (width < 0 || height < 0) {
            throw new InvalidLandDimensionException("Land dimensions must be positive");
        }
    }
}
