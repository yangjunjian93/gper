package com.yjj.study.flyweigh;

public class Test {

    public static void main(String[] args) {


        DBConnection connection = DBPool.getConnection();

        System.out.println("=====================");

        DBPool.returnConnection(connection);

    }

}
