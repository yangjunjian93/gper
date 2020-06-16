package com.yjj.study;

import java.util.concurrent.ConcurrentHashMap;

public class Demo {

    private static ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

    public static void main(String[] args) {

        concurrentHashMap.put("aaa",123);
        Object aaa = concurrentHashMap.get("aaa");
        int size = concurrentHashMap.size();

        System.out.println(aaa.toString());
        System.out.println(size);


    }



}
