package com.yjj.study.thread.lock;

import java.util.ArrayList;
import java.util.List;

public class Registry {

    List<Account> list = new ArrayList<>();

    synchronized public boolean get(Account from, Account to){

        if(list.contains(from ) || list.contains(to)){
            return false;
        }
        list.add(from);
        list.add(to);
        return true;

    }

    synchronized public void free(Account from, Account to){
        list.remove(from);
        list.remove(to);
    }


}
