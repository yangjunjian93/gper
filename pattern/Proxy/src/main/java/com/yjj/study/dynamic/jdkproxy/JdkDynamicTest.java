package com.yjj.study.dynamic.jdkproxy;

import sun.misc.ProxyGenerator;

import java.io.*;

/**
 * 动态代理的好处：无论扩展多少方法，代理的时候都会自动进行增强，无需手动配置！
 */
public class JdkDynamicTest {

    public static void main(String[] args) throws Exception{
        IDao dao = new JdkDynamicProxy().getInstance(new OrderDao());

        dao.insert();
        dao.delete();

        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{IDao.class});
        FileOutputStream fos = new FileOutputStream(new File("C:\\$Proxy0.class"));
        fos.write(bytes);

        fos.close();

    }

}
