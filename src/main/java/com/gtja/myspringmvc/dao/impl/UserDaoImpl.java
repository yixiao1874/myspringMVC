package com.gtja.myspringmvc.dao.impl;

import com.gtja.myspringmvc.dao.UserDao;

public class UserDaoImpl implements UserDao {
    @Override
    public void insert() {
        System.out.println("execute UserDaoImpl.insert()");
    }
}
