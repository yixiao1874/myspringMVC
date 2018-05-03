package com.gtja.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by 14999 on 2018/5/2.
 */
public class MyBaseExecutor implements MyExecutor {
    private static final String CONNECTIONURL =
            "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1874";
    @Override
    public <T> T query(String statment) {
        //JDBC完成DB操作获取结果
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection(CONNECTIONURL,USERNAME,PASSWORD);
            String sql = statment;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            Student student = null;
            if (resultSet.next()){
                student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setScore(resultSet.getInt("score"));
                student.setCourse(resultSet.getString("course"));
            }
            return (T)student;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
















