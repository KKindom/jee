package com.itheima.service.impl;

import com.itheima.dao.AuthorityMapper;
import com.itheima.dao.UserMapper;
import com.itheima.model.domain.Authority;
import com.itheima.service.IAuthority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorityServiceImpl implements IAuthority {
    @Autowired
    AuthorityMapper authorityMapper;

    @Override
    public void addAuthroity(Authority authority){

        authorityMapper.authority(authority);
    }
}
