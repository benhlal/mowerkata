package com.youness.mowerkata.command;


@FunctionalInterface
public interface ICommand {
    void execute(IcommandableDevice device);
}
