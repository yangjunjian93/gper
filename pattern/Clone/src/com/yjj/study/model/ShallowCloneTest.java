package com.yjj.study.model;

import com.yjj.study.model.User;

import java.util.ArrayList;
import java.util.List;

public class ShallowCloneTest {

    public static void main(String[] args) throws Exception {

        List<String> aihao = new ArrayList<String>();
        aihao.add("打球");
        aihao.add("听歌");
        aihao.add("看片");
        User user1 = new User("张三", 3333,1, aihao);
        System.out.println("原型对象 ======== " + user1);
        User user2 = user1.deepClone();
        System.out.println("新对象 =========" + user2);


        user2.getAihao().add("打架");
        System.out.println("修改后原型对象 ======== " + user1);
        System.out.println("修改后新对象 =========" + user2);

        System.out.println(user1 == user2);
        System.out.println(user1.getAihao() == user2.getAihao());

    }

}
