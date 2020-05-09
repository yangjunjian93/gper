package com.yjj.study.command;

public class UploadCommand implements ICommand{

    private FTPCommand ftpCommand;

    public void execut() {
        ftpCommand.upload();
    }
}
