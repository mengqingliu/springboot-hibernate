package com.sdnuode.nuotec.hibernate.Service;

import com.sdnuode.nuotec.hibernate.Entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
}
