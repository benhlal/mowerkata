package com.youness.mowerkata.domain;

import com.youness.mowerkata.anotation.Receiver;
import com.youness.mowerkata.command.ICommand;
import com.youness.mowerkata.command.IcommandableDevice;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

@Receiver
@Slf4j
@Getter
public class Mower implements IcommandableDevice {

    private Position position;
    private GrassLand grassLand;

    private Queue<ICommand> commandQueue;

    public Mower(GrassLand grassLand, int x, int y, Direction direction) {

        //setup mower initial position
        Position mowerPosition = new Position(x, y, direction);

        if (!grassLand.isCoordinatesIntoValid(mowerPosition.getCoordinates())) {
            log.error("New position is outside");
        }

        this.grassLand = grassLand;
        this.position = mowerPosition;
        this.commandQueue = new LinkedList<>();
    }

    @Override
    public void turnLeft() {
        log.info("Perform turn Left Command ");
        position.setDirection(position.getDirection().rotateLeft());
    }

    @Override
    public void turnRight() {
        log.info("Perform turn Right Command ");
        log.info("previous position " + position.getCoordinates().getX() + " " + position.getCoordinates().getY() + " " + position.getDirection());
        Direction newDirection = position.getDirection().rotateRight();
        position.setDirection(newDirection);
        log.info("after turn right position " + position.getCoordinates().getX() + " " + position.getCoordinates().getY() + " " + position.getDirection());

    }

    @Override
    public void advanceForward() {
        log.info("before advanceForward " + position.getCoordinates().getX() + " " + position.getCoordinates().getY() + " " + position.getDirection());

        Coordinate newCoordinate = getFutureCoordinates();

        // check that we're not exceeding outside the land
        if (grassLand.isCoordinatesIntoValid(newCoordinate)) {
            position.getCoordinates().setX(newCoordinate.getX());
            position.getCoordinates().setY(newCoordinate.getY());

        } else {
            log.warn("Next position is outside the grass moving to next command");
        }
    }

    private Coordinate getFutureCoordinates() {

        Coordinate currentPosition = position.getCoordinates();
        int x = currentPosition.getX();
        int y = currentPosition.getY();
        Direction direction = position.getDirection();

        x += direction.getX();
        y += direction.getY();

        return new Coordinate(x, y);
    }


    /***
     *
     * Run  One command from commands Queue
     * **/
    @Override
    public String start() {
        //  log.info("****** [start]executing command in Start : queue {}, number of commands ****", this.commandQueue.toString());
        Optional<ICommand> optCommand = Optional.ofNullable(this.commandQueue.poll());
        if (optCommand.isPresent()) {
            optCommand.get().execute(this);
        }
        return this.toString();
    }

    @Override
    public String hold() {
        log.info("Pause waiting for correct command");
        return "Pause";

    }

    /***
     * Run all commands
     * **/
    @Override
    public String startAll() {
        log.info("****** [startAll] executing all commands  by mower  at : {} , number of commands in queue : {} ****", this.toString(), this.getCommandQueue().size());

        //  this.commandQueue.forEach(this::addAction);
        while (!this.commandQueue.isEmpty()) {
            this.start();
        }
        return this.toString();
    }


    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void addToCommandQueue(ICommand cmd) {
        this.commandQueue.add(cmd);
    }

    @Override
    public String toString() {
        Coordinate coordinates = position.getCoordinates();
        return coordinates.getX() + " " + coordinates.getY() + " " + position.getDirection().toString();
    }
}
