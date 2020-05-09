package com.yjj.study.dynamic.yjjproxy.proxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class YjjProxy {

    private static final String ln = "\r\n";

    // 根据传入的字节码去获取一个代理对象
    public static Object getProxyInstance(YjjClassLoader classLoader,
                                   Class<?>[] interfaces,
                                   YjjInvocationHandler h){
        try {
            // 1、根据字节码生成java文件字符串
            String str = generateStr(interfaces);
            // 2、把Java文件输出到磁盘
            String filePath = YjjProxy.class.getResource("").getPath();
            File file = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(file);
            fw.write(str);
            fw.close();


            // 3、读取Java文件编译成Class文件
            // 通过工具类获取java编译器
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            // 获取文件管理
            StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
            // 读取java文件
            Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(file);
            // 创建编译任务
            JavaCompiler.CompilationTask task = compiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
            // 执行
            task.call();
            // 关流
            standardFileManager.close();

            // 4、把Class文件加载到JVM虚拟机中
            Class<?> proxyClass = classLoader.findClass("$Proxy0");
            // 获取构造器，这里不直接newInstance是为了防止对象的有参构造
            // 参考生成的$Proxy0.java文件，构造方法我们是有传一个InvocationHandler的，所以丢个接口字节码进去
            Constructor<?> constructor = proxyClass.getConstructor(YjjInvocationHandler.class);

            // 5、根据class对象生成实例返回
            return constructor.newInstance(h);
        } catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 根据字节码生成java文件字符串
     */
    private static String generateStr(Class<?>[] interfaces) {

        StringBuilder sb = new StringBuilder();
        // 设置包名为当前代理类的包
        sb.append(YjjProxy.class.getPackage() + ";" + ln);
        // 导入接口和反射包
        for (Class<?> anInterface : interfaces) {
            sb.append("import " + anInterface.getName() + ";" + ln);
        }
        sb.append("import java.lang.reflect.*;" + ln);
        sb.append("import com.yjj.study.dynamic.yjjproxy.proxy.YjjInvocationHandler;" + ln);
        // 声明类，实现传入的接口
        sb.append("public class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);
            // 设置自定义的类加载器,通过构造方法传入
            sb.append("YjjInvocationHandler h;" + ln);
            sb.append("public $Proxy0(YjjInvocationHandler h){" + ln);
                sb.append("this.h = h;" + ln);
            sb.append("}" + ln);
            
            // 然后循环实现接口的方法
        for (Method method : interfaces[0].getMethods()) {
            // 如果是有参方法，拿到参数类型列表
            Class<?>[] parameterTypes = method.getParameterTypes();

            String paramName = "var";
            int num = 1;
            // 设置拼接入参列表
            StringBuilder paramList = new StringBuilder();
            StringBuilder returnList = new StringBuilder();
            StringBuilder classList = new StringBuilder();
            // 循环参数列表设置
            for (Class<?> parameterType : parameterTypes) {
                // 拿到参数类型路径名，如java.lang.String
                String name = parameterType.getName();
                // 设置入参列表，拼接结果为java.lang.String var1
                paramList.append(name + " " + paramName + num);
                returnList.append(paramName + num);
                classList.append(name + ".class");
                paramList.append(",");
                returnList.append(",");
                classList.append(",");
                num++;
            }

            // 拼接方法类型 出参 方法名 入参类型 入参名 PS：这里只做了无参方法的！晚点自定义实现有参的！
            sb.append("public " + method.getReturnType().getName() + " " + method.getName() + "(" + hanlderString(paramList) + ") {" + ln);
            // 拼接反射执行方法的语句，加上try/catch
                sb.append("try{" + ln);
                    sb.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + method.getName() + "\",new Class[]{" + hanlderString(classList) + "});" + ln);
                    sb.append((hasReturnValue(method.getReturnType()) ? "return " : "") + getCaseCode("this.h.invoke(this,m,new Object[]{" + hanlderString(returnList) + "})",method.getReturnType()) + ";" + ln);
                sb.append("}catch(Error _ex) { }" + ln);
                sb.append("catch(Throwable e){" + ln);
                    sb.append("throw new UndeclaredThrowableException(e);" + ln);
                sb.append("}" + ln);
                sb.append(getReturnEmptyCode(method.getReturnType()));
            sb.append("}" + ln);
        }

        sb.append("}");
        return sb.toString();
    }


    private static Map<Class,Class> mappings = new HashMap<Class, Class>();
    static {
        mappings.put(int.class,Integer.class);
    }

    private static String getReturnEmptyCode(Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "return 0;";
        }else if(returnClass == void.class){
            return "";
        }else {
            return "return null;";
        }
    }

    private static String getCaseCode(String code,Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "((" + mappings.get(returnClass).getName() +  ")" + code + ")." + returnClass.getSimpleName() + "Value()";
        }
        return code;
    }

    private static boolean hasReturnValue(Class<?> clazz){
        return clazz != void.class;
    }

    private static String hanlderString(StringBuilder sb){

        if(sb == null || "".equals(sb.toString())){
            return "";
        }

        String str = sb.toString();
        if(str.lastIndexOf(",") == str.length() - 1){
            str = str.substring(0, str.length()-1);
        }
        return str;
    }


}
