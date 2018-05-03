package com.gtja.mybatis;

/**
 * Created by 14999 on 2018/5/2.
 */
public interface MyExecutor {
    public<T> T query(String statment);
}
