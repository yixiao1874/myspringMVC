package com.gtja.mybatis;

/**
 * Created by 14999 on 2018/5/2.
 */
public class BootStrap {
    public static void main(String[] args) {
        start();
    }

    private static void start(){
        MySqlSession sqlSession = new MyDefaultSqlSession();

        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        Student student = studentMapper.findStudentById(1);

        System.out.println(student);
    }
}
