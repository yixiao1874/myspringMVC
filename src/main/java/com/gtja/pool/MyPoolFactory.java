package com.gtja.pool;

public class MyPoolFactory {
    public static class CreatePool{
        public static IMyPool myPool = new MyDefaultPool();
    }

    public static IMyPool getInstance(){
        System.out.println("===============");
        return CreatePool.myPool;
    }
}
