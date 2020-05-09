package com.yjj.study.model;

import java.util.ArrayList;
import java.util.List;

public class MyDirectory extends SystemFile{

    private List<SystemFile> list;

    private Integer level;

    public MyDirectory(String name, int level) {
        super(name);
        this.list = new ArrayList<SystemFile>();
        this.level = level;

    }

    public void add(SystemFile file){
        list.add(file);
    }

    @Override
    public void show() {

        System.out.println(this.name);
        for (SystemFile file : list) {

            if(level != null){
                for (int i = 0; i < level; i++) {
                    System.out.print("   ");

                }
                for (int i = 0; i < level; i++) {
                    //每一行开始打印一个+号
                    if(i == 0){ System.out.print("+"); }
                    System.out.print("-");
                }


            }
            file.show();
        }


    }
}
