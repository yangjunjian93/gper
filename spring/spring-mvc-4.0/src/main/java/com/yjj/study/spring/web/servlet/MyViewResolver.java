package com.yjj.study.spring.web.servlet;

import java.io.File;

public class MyViewResolver {

    private final String DEFAULT_TEMPLATE_SUFFIX = ".html";

    private File templateRootDir;

    // 创建视图解析器，在当前代码中是定位模版所在文件夹
    public MyViewResolver(String templateRoot) {

        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);

    }

    // 根据视图名称在模版文件夹定位模版
    public MyView resolveViewName(String viewName){

        if(null == viewName || "".equals(viewName.trim())){
            return null;
        }

        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX)? viewName : viewName + DEFAULT_TEMPLATE_SUFFIX;

        File file = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+", "/"));
        return new MyView(file);

    }



}
