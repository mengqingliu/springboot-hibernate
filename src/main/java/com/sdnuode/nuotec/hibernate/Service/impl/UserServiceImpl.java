package com.sdnuode.nuotec.hibernate.Service.impl;


import com.sdnuode.nuotec.hibernate.Dao.UserJPA;
import com.sdnuode.nuotec.hibernate.Entity.User;
import com.sdnuode.nuotec.hibernate.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJPA userJPA;

    @Override
    public List<User> findAll() {
        // 这里我们就可以直接使用 findAll 方法
        return userJPA.findAll();
    }
}
