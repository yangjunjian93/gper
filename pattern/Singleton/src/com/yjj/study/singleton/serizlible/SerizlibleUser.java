package com.yjj.study.singleton.serizlible;

import com.yjj.study.singleton.service.IUser;

import java.io.Serializable;

public class SerizlibleUser implements IUser, Serializable {

    private static SerizlibleUser user = new SerizlibleUser();

    private SerizlibleUser (){

    }

    public static IUser getUser(){
        return user;
    }

    private Object readResolve(){ return user;}

}
