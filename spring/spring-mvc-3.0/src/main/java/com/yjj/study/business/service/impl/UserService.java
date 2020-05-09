package com.yjj.study.business.service.impl;

import com.yjj.study.business.service.IUserService;
import com.yjj.study.spring.annotation.MyService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MyService
public class UserService implements IUserService {

    @Override
    public String handlerStr(String name) {

        return name + " , this form UserServiceImpl";

    }

    @Override
    public String query(String teacher) {
        return teacher + ", 当前时间: " +  LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) ;
    }
}
