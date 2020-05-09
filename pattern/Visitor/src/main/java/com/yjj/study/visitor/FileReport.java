package com.yjj.study.visitor;

import com.yjj.study.visitor.employee.Employee;
import com.yjj.study.visitor.employee.Leader;
import com.yjj.study.visitor.employee.Programmer;
import com.yjj.study.visitor.visitorimpl.IVisitor;

import java.util.ArrayList;
import java.util.List;

// 封装的报表需求
public class FileReport {

    private List<Employee> list = new ArrayList<Employee>();

    public FileReport() {
        list.add(new Leader("张组长"));
        list.add(new Leader("王组长"));
        list.add(new Programmer("赵码农"));
        list.add(new Programmer("钱码农"));
        list.add(new Programmer("孙码农"));
    }

    public void showReport(IVisitor visitor){

        for (Employee employee : list) {
            employee.accept(visitor);
        }

    }

}
