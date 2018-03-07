package com.w77996.seckill.service;

import com.w77996.seckill.dao.UserDao;
import com.w77996.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getById(long id){
        return userDao.getById(id);
    }
}
