package com.gtja.mybatis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 14999 on 2018/5/2.
 */
public class StudentMapperXML {
    public static final String namespace = "com.gtja.mybatis";
    private static Map<String,String> methodSqlMap = new HashMap<String, String>();
    static {
        methodSqlMap.put("findStudentById","select * from student where id = %s");
    }

    public static String getMethodSQL(String method){
        return methodSqlMap.get(method);
    }
}
