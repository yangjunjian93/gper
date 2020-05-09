package com.yjj.study.business.controller;


import com.yjj.study.business.service.IUserService;
import com.yjj.study.spring.annotation.MyAutowired;
import com.yjj.study.spring.annotation.MyController;
import com.yjj.study.spring.annotation.MyRequestMapping;
import com.yjj.study.spring.annotation.MyRequestParam;
import com.yjj.study.spring.web.servlet.MyModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@MyController
public class UserController {

    @MyAutowired
    private IUserService userService;


    @MyRequestMapping("query")
    public MyModelAndView query(HttpServletRequest req, HttpServletResponse resp,
                                @MyRequestParam("name") String name){

        String outStr = "Hi! Everybody ! My name is " + name;
        outStr = userService.handlerStr(outStr);
        try{
            resp.getWriter().write(outStr);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @MyRequestMapping("/first.html")
    public MyModelAndView query(@MyRequestParam("teacher") String teacher){
        String result = userService.query(teacher);
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("teacher", teacher);
        model.put("data", result);
        model.put("token", "123456");
        return new MyModelAndView("first.html",model);
    }

}
