package com.gtja.pool;

public class DBConfigXML {
    public static final String jdbcDriver = "com.mysql.jdbc.Driver";
    public static final String jdbcURL = "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8";
    public static final String jdbcUsername = "root";
    public static final String jdbcPassword = "1874";
    //数据库连接池初始化大小
    public static final int initCount = 4;
    //增长速度
    public static final int step = 2;
    //连接池的最大数量
    public static final int maxCount = 4;
}
