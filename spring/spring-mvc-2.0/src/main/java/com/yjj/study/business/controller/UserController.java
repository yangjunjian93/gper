package com.yjj.study.business.controller;


import com.yjj.study.business.service.IUserService;
import com.yjj.study.spring.annotation.MyAutowired;
import com.yjj.study.spring.annotation.MyController;
import com.yjj.study.spring.annotation.MyRequestMapping;
import com.yjj.study.spring.annotation.MyRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MyController
public class UserController {

    @MyAutowired
    private IUserService userService;


    @MyRequestMapping("query")
    public void query(HttpServletRequest req, HttpServletResponse resp,
                      @MyRequestParam("name") String name){

        String outStr = "Hi! Everybody ! My name is " + name;
        outStr = userService.handlerStr(outStr);

        try{
            resp.getWriter().write(outStr);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
