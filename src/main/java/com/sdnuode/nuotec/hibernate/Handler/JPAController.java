package com.sdnuode.nuotec.hibernate.Handler;

import com.sdnuode.nuotec.hibernate.Dao.UserJPA;
import com.sdnuode.nuotec.hibernate.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class JPAController {
    @Autowired
    private UserJPA userJPA;

    /**
     * 数据新增或更新，save方法可以执行添加也可以执行更新，如果需要执行持久化的实体存在主键值则更新数据，如果不存在则添加数据。
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public User save(@RequestBody User user) {
        return userJPA.save(user);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public List<User> selectAll(){
        return userJPA.findAll();
    }

//    @RequestMapping(value = "/page", method = RequestMethod.GET)
//    public Page<User> page(){
//        return userJPA.findAll(true);
//    }
}
