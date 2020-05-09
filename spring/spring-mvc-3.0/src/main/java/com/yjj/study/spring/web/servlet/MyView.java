package com.yjj.study.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyView {

    private File template;

    public MyView(File template) {
        this.template = template;
    }


    // 返回model对象
    public void render(Map<String,?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        StringBuilder sb = new StringBuilder();
        RandomAccessFile raf = new RandomAccessFile(template, "r");

        String line;

        // 匹配模版特殊占位符
        while (null != (line = raf.readLine())){
            // 进行转码
            String context = new String(line.getBytes("ISO-8859-1"), "UTF-8");
            // 正则匹配占位符，替换为相应的参数
            Pattern pattern = Pattern.compile("￥\\{[^\\}]+\\}", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(context);
            while (matcher.find()){
                String paramName = matcher.group();
                paramName = paramName.replaceAll("￥\\{|\\}","");
                Object paramValue = model.get(paramName);
                context = matcher.replaceFirst(makeStringForRegExp(paramValue.toString()));
            }
            sb.append(context);
        }
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write(sb.toString());
    }

    //处理特殊字符
    public static String makeStringForRegExp(String str) {
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }


}
