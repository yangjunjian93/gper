package com.yjj.study.visitor.visitorimpl;

import com.yjj.study.visitor.employee.Leader;
import com.yjj.study.visitor.employee.Programmer;

// 设置访问者，拥有具体的访问对象
public interface IVisitor {

    void visitor(Leader leader);

    void visitor(Programmer programmer);

}
