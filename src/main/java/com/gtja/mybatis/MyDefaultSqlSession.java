package com.gtja.mybatis;

import java.lang.reflect.Proxy;

/**
 * Created by 14999 on 2018/5/2.
 */
public class MyDefaultSqlSession implements MySqlSession {
    private MyExecutor executor = new MyBaseExecutor();
    @Override
    public <T> T selectOne(String sql) {
        return executor.query(sql);
    }

    @Override
    public <T> T getMapper(Class<T> interfaces) {
        return (T) Proxy.newProxyInstance(interfaces.getClassLoader(),new Class[]{interfaces},
                new MyMapperProxy(this));
    }
}
