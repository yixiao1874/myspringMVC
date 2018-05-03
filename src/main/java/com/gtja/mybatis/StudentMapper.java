package com.gtja.mybatis;

/**
 * Created by 14999 on 2018/5/2.
 */
public interface StudentMapper {
    public Student findStudentById(int id);
    public void insertStudent(Student student);
}
