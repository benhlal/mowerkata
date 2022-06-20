package com.youness.mowerkata.domain;

import com.youness.mowerkata.exception.InvalidDirectionException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum Direction {
    NORTH("N", "W", "E", 0, 1),
    EAST("E", "N", "S", 1, 0),
    SOUTH("S", "E", "W", 0, -1),
    WEST("W", "S", "N", -1, 0);

    private String code;

    private String left;

    private String right;

    private int x;

    private int y;


    public Direction rotateRight() {
        try {
            return Direction.getDirectionByCode(this.right);
        } catch (InvalidDirectionException e) {
            return null;
        }
    }

    public Direction rotateLeft() {
        try {
            return Direction.getDirectionByCode(this.left);
        } catch (InvalidDirectionException e) {
            return null;
        }
    }

    public static Direction getDirectionByCode(String code) throws InvalidDirectionException {
        Optional<Direction> directionOpt = Arrays.stream(values()).filter(direction -> direction.code.equals(code)).findFirst();
        if (directionOpt.isPresent()) {
            return directionOpt.get();
        } else throw new InvalidDirectionException("Invalid Direction input");
    }

    @Override
    public String toString() {
        return this.code;
    }
}
