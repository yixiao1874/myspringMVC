package com.gtja.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyPooledConnection {
    private Connection connection;
    private boolean isBusy = false;

    public MyPooledConnection(Connection connection, boolean isBusy) {
        this.connection = connection;
        this.isBusy = isBusy;
    }

    public void close(){
        this.isBusy = false;
    }

    public ResultSet query(String sql){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }
}
