package com.itheima.service.impl;

import com.itheima.dao.UserMapper;
import com.itheima.model.domain.User;
import com.itheima.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void addUser(User user){
        user.setDate(new Date());
        userMapper.register(user);
    }
}
