package com.youness.mowerkata.command;

public class AdvanceCommand implements ICommand {

    public AdvanceCommand() {
    }

    @Override
    public void execute(IcommandableDevice mower) {
        mower.advanceForward();
    }
}
