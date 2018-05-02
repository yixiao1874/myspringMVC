package com.gtja.pool;

public interface IMyPool {
    MyPooledConnection getMyPooledConnection();
    void createMyPooledConnection(int count);
}
