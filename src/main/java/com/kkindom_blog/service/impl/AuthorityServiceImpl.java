package com.kkindom_blog.service.impl;

import com.kkindom_blog.dao.AuthorityMapper;
import com.kkindom_blog.model.domain.Authority;
import com.kkindom_blog.service.IAuthority;

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
