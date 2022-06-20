package com.youness.mowerkata.command;

import com.youness.mowerkata.domain.Position;

public interface IcommandableDevice {


    String start();

    String hold();

    String startAll();

    void turnLeft();

    void turnRight();

    void advanceForward();

    void addToCommandQueue(ICommand cmd);

    Position getPosition();


}