package com.gtja.mybatis;

/**
 * Created by 14999 on 2018/5/2.
 */
public interface MySqlSession {
    <T> T selectOne(String var1);
    <T> T getMapper(Class<T> var1);
}
