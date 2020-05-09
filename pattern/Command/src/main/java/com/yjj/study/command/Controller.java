package com.yjj.study.command;

public class Controller {

    private ICommand command;

    public Controller(ICommand command) {
        this.command = command;
    }

    public void execut(){

        command.execut();

    }

}
