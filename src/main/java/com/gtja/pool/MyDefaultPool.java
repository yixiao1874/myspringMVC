package com.gtja.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MyDefaultPool implements IMyPool {
    private Vector<MyPooledConnection> myPooledConnectionVector = new Vector<>();
    private static String jdbcURL;
    private static String jdbcUsername;
    private static String jdbcPassword;
    private static int initCount;
    private static int step;
    private static int maxCount;

    public MyDefaultPool() {
        init();
        try {
            Class.forName(DBConfigXML.jdbcDriver);
        }catch (Exception e){
            e.printStackTrace();
        }

        createMyPooledConnection(initCount);
    }

    public void init(){
        jdbcURL = DBConfigXML.jdbcURL;
        jdbcUsername = DBConfigXML.jdbcUsername;
        jdbcPassword = DBConfigXML.jdbcPassword;
        initCount = DBConfigXML.initCount;
        step = DBConfigXML.step;
        maxCount = DBConfigXML.maxCount;
    }

    @Override
    public MyPooledConnection getMyPooledConnection() {
        if (myPooledConnectionVector.size() < 1){
            throw new RuntimeException("连接池初始化错误！");
        }
        MyPooledConnection myPooledConnection = null;
        try{
            myPooledConnection = getRealConnectionFromPool();
            while (myPooledConnection == null){
                createMyPooledConnection(step);
                myPooledConnection = getRealConnectionFromPool();
                return myPooledConnection;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return myPooledConnection;
    }

    private synchronized MyPooledConnection getRealConnectionFromPool() throws Exception{
        for (MyPooledConnection myPooledConnection:myPooledConnectionVector){
            if (!myPooledConnection.isBusy()){
                if (myPooledConnection.getConnection().isValid(3000)){
                    myPooledConnection.setBusy(true);
                    return myPooledConnection;
                }else {
                    Connection connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
                    myPooledConnection.setConnection(connection);
                    myPooledConnection.setBusy(true);
                    return myPooledConnection;
                }
            }
        }
        return  null;
    }

    @Override
    public void createMyPooledConnection(int count) {
        if (myPooledConnectionVector.size()>maxCount ||
                myPooledConnectionVector.size() + count>maxCount ){
            throw new RuntimeException("连接池已满");
        }
        for (int i =0; i<count; i++){
            try {
                Connection connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
                MyPooledConnection myPooledConnection = new MyPooledConnection(connection,false);
                myPooledConnectionVector.add(myPooledConnection);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
