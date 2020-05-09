package com.yjj.study.visitor.visitorimpl;

import com.yjj.study.visitor.employee.Leader;
import com.yjj.study.visitor.employee.Programmer;

// CTO关注员工做了什么，也是独有的需求
public class CTOVisitor implements IVisitor{


    public void visitor(Leader leader) {
        System.out.println("项目经理：" + leader.name + ", 今年负责产品数量为：" + leader.getProductNum());
    }

    public void visitor(Programmer programmer) {
        System.out.println("工程师：" + programmer.name + ", 今年负责代码量为：" + programmer.getCodeLine());
    }
}
