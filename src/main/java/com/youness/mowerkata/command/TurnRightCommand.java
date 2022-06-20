package com.youness.mowerkata.command;

public class TurnRightCommand implements ICommand {
    protected void TurnRightCommand() {
    }

    @Override
    public void execute(IcommandableDevice mower) {
        mower.turnRight();
    }
}
