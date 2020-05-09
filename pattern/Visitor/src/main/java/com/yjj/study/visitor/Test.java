package com.yjj.study.visitor;

import com.yjj.study.visitor.visitorimpl.CEOVisitor;
import com.yjj.study.visitor.visitorimpl.CTOVisitor;

public class Test {

    public static void main(String[] args) {

        FileReport fileReport = new FileReport();
        fileReport.showReport(new CTOVisitor());
        System.out.println("=============================");
        fileReport.showReport(new CEOVisitor());


    }

}
