package com.yjj.study.dynamic.yjjproxy.client;

public interface IDao {

    void insert();

    void delete();

    void update(String id, int num, boolean flag);
}
