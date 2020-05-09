package com.yjj.study.command;

public class RemoveCommand implements ICommand{

    private FTPCommand ftpCommand;

    public void execut() {
        ftpCommand.remove();
    }
}
