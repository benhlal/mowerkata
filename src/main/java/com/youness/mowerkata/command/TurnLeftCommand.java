package com.youness.mowerkata.command;

public class TurnLeftCommand implements ICommand {
    public TurnLeftCommand() {
    }

    @Override
    public void execute(IcommandableDevice mower) {
        mower.turnLeft();
    }
}
