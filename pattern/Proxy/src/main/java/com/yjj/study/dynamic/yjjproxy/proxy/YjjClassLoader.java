package com.yjj.study.dynamic.yjjproxy.proxy;

import javax.naming.Name;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 自定义一个ClassLoader，其实主要功能肯定还是由JDK的ClassLoader去实现
 * 在这里继承ClassLoader主要是为了重写一下他去找Class的方法，指定去查找Class的路径
 * 因为要实现自定义的动态代理，肯定生成的代理类的路径与原生不一致
 */
public class YjjClassLoader extends ClassLoader{


    private File classPathFile;

    public YjjClassLoader(){
        // 直接把项目的相对路径下所有的类读取成File对象
        String path = YjjClassLoader.class.getResource("").getPath();
        this.classPathFile = new File(path);

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        // 根据传进来的类名去设置类路径，这个类按照之前的生成在
        // 这里我们在YjjProxy中设置了java文件和当前类加载器处于同一路径下，直接获取当前类加载器路径拼接名字就行
        String className = YjjClassLoader.class.getPackage().getName() + "." + name;
        if(classPathFile != null){
            // 上面已经把classPathFile设置到了当前的目录下，直接把刚才获取到的类路径的 . 替换成 / 然后生成class就行
            File file = new File(classPathFile, name.replaceAll("\\.", "/") + ".class");
            if(file.exists()){
                // IO操作
                FileInputStream fis;
                ByteArrayOutputStream bais;
                try {
                    // 把编译好的文件做为输入流
                    fis = new FileInputStream(file);
                    // 准备加载到jvm的输出流
                    bais = new ByteArrayOutputStream();
                    // 读取file文件转换为byte数组输出
                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len = fis.read(bytes)) != -1){
                        bais.write(bytes, 0, len);
                    }
                    // 最后根据类名，byte数组，索引等加载到jvm
                    return defineClass(className, bais.toByteArray(), 0 ,bais.size());
                } catch (Exception e){
                    e.printStackTrace();
                }

            }


        }




        return null;
    }
}
