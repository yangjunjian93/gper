package com.yjj.study.observer;

import com.google.common.eventbus.Subscribe;

public class GPer {
    public Question createQuestion(String userName, String content){
        Question question = new Question(userName, content);
        return question;
    }
}
