package com.yjj.study.youngbatis;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
* 访问数据库对象
*/
public class Executor {

    private Configuration configuration;

    public Executor(Configuration configuration) {
        this.configuration = configuration;
    }

    public Object query(String sql, Object[] args, String resultType) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            // 3、执行sql
            ps = getPreparedStatement(connection, sql, args);
            resultSet = ps.executeQuery();

            return handlerResultSet(resultSet, resultType);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, ps, resultSet);
        }
        return null;
    }

    public List<Object> handlerResultSet(ResultSet resultSet, String resultType) throws Exception {
        List<Object> list = new ArrayList();

        Class<?> clazz = Class.forName(resultType);
        Object obj;
        Field[] fields;
        while (resultSet.next()){
            obj = clazz.newInstance();
            fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.getType() == Integer.class || field.getType() == int.class){
                    field.set(obj, Integer.parseInt(resultSet.getObject(field.getName()).toString()));
                } else{
                    field.set(obj, resultSet.getObject(field.getName()).toString());
                }
            }
            list.add(obj);
        }
        return list;
    }

    public PreparedStatement getPreparedStatement(Connection connection, String sql, Object[] args) throws Exception {

        PreparedStatement ps = connection.prepareStatement(sql);
        // 封装参数
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
        }
        return ps;
    }

    private Connection getConnection() throws Exception {
        ResourceBundle properties = configuration.getProperties();
        // 1、加载驱动
        Class.forName(properties.getString("jdbcDrive"));
        // 2、获取链接
        Connection connection = DriverManager.getConnection(properties.getString("jdbcUrl"),
                properties.getString("jdbcUserName"), properties.getString("jdbcPassword"));
        return connection;
    }

    private void close(Connection connection, PreparedStatement ps, ResultSet resultSet) {
        // 5、关闭链接
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

} 