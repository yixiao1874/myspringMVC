package com.gtja.pool;

import java.sql.ResultSet;

public class Test {
    public static IMyPool myPool = MyPoolFactory.getInstance();

    public static void main(String[] args) {
        MyPooledConnection myPooledConnection1 =myPool.getMyPooledConnection();
        System.out.println(myPooledConnection1.getConnection());
        //myPooledConnection1.close();
        MyPooledConnection myPooledConnection2 =myPool.getMyPooledConnection();
        System.out.println(myPooledConnection2.getConnection());
        myPooledConnection2.close();
        MyPooledConnection myPooledConnection3 =myPool.getMyPooledConnection();
        System.out.println(myPooledConnection3.getConnection());
        myPooledConnection3.close();
        MyPooledConnection myPooledConnection4 =myPool.getMyPooledConnection();
        System.out.println(myPooledConnection4.getConnection());
        myPooledConnection4.close();
        MyPooledConnection myPooledConnection5 =myPool.getMyPooledConnection();
        System.out.println(myPooledConnection5.getConnection());
        //myPooledConnection5.close();
        for (int i=0; i<2; i++){
            MyPooledConnection myPooledConnection =myPool.getMyPooledConnection();
            ResultSet query = myPooledConnection.query("select * from test");
            try {
                while (query.next()){
                    System.out.println(query.getString("name") + "," +
                    "使用管道：" + myPooledConnection.getConnection());
                }
                myPooledConnection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
