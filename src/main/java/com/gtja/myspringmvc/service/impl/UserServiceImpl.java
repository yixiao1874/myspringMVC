package com.gtja.myspringmvc.service.impl;

import com.gtja.myspringmvc.annotation.Qualifier;
import com.gtja.myspringmvc.dao.UserDao;
import com.gtja.myspringmvc.service.UserService;

public class UserServiceImpl implements UserService {
    @Qualifier("userDaoImpl")
    private UserDao userDao;
    @Override
    public void insert() {
        System.out.println("UserServiceImpl.insert() start");
        userDao.insert();
        System.out.println("UserServiceImpl.insert() end");
    }
}
