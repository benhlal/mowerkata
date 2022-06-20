package com.youness.mowerkata.domain;

public class Position {
    private Coordinate coordinates;
    private Direction direction;

    public Position(int x, int y, Direction direction) {
        this.coordinates = new Coordinate(x, y);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }
}
