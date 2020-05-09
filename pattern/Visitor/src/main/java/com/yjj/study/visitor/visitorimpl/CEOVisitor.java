package com.yjj.study.visitor.visitorimpl;

import com.yjj.study.visitor.employee.Leader;
import com.yjj.study.visitor.employee.Programmer;

// CEO关注的是KPI，这是自己独有的需求
public class CEOVisitor implements IVisitor{


    public void visitor(Leader leader) {
        System.out.println("项目经理：" + leader.name + ", 当前kpi为：" + leader.kpi);
    }

    public void visitor(Programmer programmer) {
        System.out.println("工程师：" + programmer.name + ", 当前kpi为：" + programmer.kpi);
    }
}
