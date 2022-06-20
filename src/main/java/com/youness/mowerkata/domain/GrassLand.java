package com.youness.mowerkata.domain;

import com.youness.mowerkata.anotation.Invoker;
import com.youness.mowerkata.command.IcommandableDevice;
import com.youness.mowerkata.exception.IllegalMowerPositionOnGrassLand;
import com.youness.mowerkata.exception.InvalidLandDimensionException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.youness.mowerkata.util.Validators.checkDimension;

@Invoker
@Slf4j
@Getter
public class GrassLand {
    private int width;

    private int height;

    private Queue<IcommandableDevice> mowerQueue;

    public GrassLand(int width, int height) throws InvalidLandDimensionException {
        checkDimension(width, height);
        this.width = width;
        this.height = height;
        this.mowerQueue = new LinkedList<>();
    }


    public boolean isCoordinatesIntoValid(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        boolean isXValid = x >= 0 && x <= this.width;
        boolean isYValid = y >= 0 && y <= this.height;
        return isXValid && isYValid;
    }


    public void addToMowersQueueOnGrassLand(IcommandableDevice mower) throws IllegalMowerPositionOnGrassLand {
        // todo:: prevent adding 2 mowers to same position
        this.mowerQueue.add(mower);
    }


    public int availableMowersCount() {
        return mowerQueue.size();
    }


    public Map<Position, String> invokeStartAll() {
        Map<Position, String> allPositions = new HashMap();
        Optional<Queue<IcommandableDevice>> commandableDevicesQ = Optional.ofNullable(this.mowerQueue);
        commandableDevicesQ.ifPresent(
                commandableDevices -> commandableDevices.forEach(
                        device -> {
                            String finishPosition = device.startAll();
                            allPositions.put(device.getPosition(), finishPosition);

                        }));

        return allPositions;
    }

    private void logDeviceInfo(IcommandableDevice device) {
        Optional<Coordinate> coordinatesOpt = Optional.ofNullable(device.getPosition().getCoordinates());
        Optional<Position> positionOpt = Optional.ofNullable(device.getPosition());
        if (positionOpt.isPresent()) {
            if (coordinatesOpt.isPresent())
                log.info("Invoke commands execution on device(Mower) at starting position : [{},{},{}]", device.getPosition().getCoordinates().getX(), device.getPosition().getCoordinates().getY(), device.getPosition().getDirection().toString());
        }
    }
}
