package com.yjj.study.mybatis.mapper;

import com.yjj.study.mybatis.model.Author;

public interface AuthorMapper {
    int deleteByPrimaryKey(Integer authorId);

    int insert(Author record);

    int insertSelective(Author record);

    Author selectByPrimaryKey(Integer authorId);

    int updateByPrimaryKeySelective(Author record);

    int updateByPrimaryKey(Author record);
}