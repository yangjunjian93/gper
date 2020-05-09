package com.yjj.study.command;

public class Test {

    public static void main(String[] args) {


        Controller controller = new Controller(new CreateDirCommand(new FTPCommand()));
        controller.execut();


    }

}
