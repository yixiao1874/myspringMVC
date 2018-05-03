package com.gtja.mybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by 14999 on 2018/5/2.
 */
public class MyMapperProxy implements InvocationHandler {
    private MySqlSession sqlSession;

    public MyMapperProxy(){}

    public MyMapperProxy(MySqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperClass = method.getDeclaringClass().getName();
        //  com.gtja.mybatis.StudentMapper
        mapperClass = mapperClass.substring(0,mapperClass.lastIndexOf("."));
        System.out.println(mapperClass);
        if (StudentMapperXML.namespace.equals(mapperClass)){
            String methodName = method.getName();
            String originSql = StudentMapperXML.getMethodSQL(methodName);
            String formattedSql = String.format(originSql,String.valueOf(args[0]));
            return sqlSession.selectOne(formattedSql);
        }
        return null;
    }
}
