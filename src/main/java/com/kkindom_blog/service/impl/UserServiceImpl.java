package com.kkindom_blog.service.impl;

import com.kkindom_blog.dao.UserMapper;
import com.kkindom_blog.model.domain.User;
import com.kkindom_blog.service.IUserService;
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

    @Override
    public void updateUser(User user){
        userMapper.updateUser(user);
    }
}
