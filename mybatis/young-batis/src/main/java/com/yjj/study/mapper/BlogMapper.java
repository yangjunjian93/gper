package com.yjj.study.mapper;

import com.yjj.study.model.Blog;

import java.util.List;

public interface BlogMapper {

    List<Blog> queryBlogById(int id);

}
