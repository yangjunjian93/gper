package com.yjj.study.business.service.impl;

import com.yjj.study.business.service.IUserService;
import com.yjj.study.spring.annotation.MyService;

@MyService
public class UserService implements IUserService {

    @Override
    public String handlerStr(String name) {

        return name + " , this form UserServiceImpl";

    }

}
