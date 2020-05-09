package com.yjj.study.flyweigh;

import java.util.ArrayList;
import java.util.List;

public class DBPool {

    private static List<DBConnection> list;

    static{
        list = new ArrayList<DBConnection>();
        for (int i = 0; i < 100; i++) {
             list.add(new DBConnection());

        }
    }

    public static DBConnection getConnection(){

        if(list != null && list.size() != 0){

            DBConnection dbConnection = list.get(0);
            System.out.println("从连接池中拿到了一个链接");
            list.remove(dbConnection);
            System.out.println("目前连接池内还有" + list.size() + "个连接");
            return dbConnection;

        }
        return null;
    }

    public static void returnConnection(DBConnection conn){

        list.add(conn);
        System.out.println("归还了一个连接到连接池");
        System.out.println("目前连接池内还有" + list.size() + "个连接");
    }

}
