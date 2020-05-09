package com.yjj.study.command;

public class CreateDirCommand implements ICommand{

    private FTPCommand ftpCommand;

    public CreateDirCommand(FTPCommand ftpCommand) {
        this.ftpCommand = ftpCommand;
    }

    public void execut() {
        ftpCommand.createDir();
    }
}
