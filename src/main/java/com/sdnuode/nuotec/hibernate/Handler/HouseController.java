package com.sdnuode.nuotec.hibernate.Handler;

import com.sdnuode.nuotec.hibernate.Dao.HouseRepository;
import com.sdnuode.nuotec.hibernate.Entity.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HouseController
 * @Description cache缓存测试
 * @Author mengq
 * @Date 2018/12/13 15:19
 **/
@RestController
@CacheConfig(cacheNames = {"myCache"})
public class HouseController {

    @Autowired
    private HouseRepository houseRepository;

    //http://localhost:8084/saveHouse?id=1&houseName=别墅&houseSize=1220平方米
    // 方法中使用到了@CachePut注解，这个注解直接将返回值放入缓存中，通常用于保存和修改方法中
    @GetMapping("/saveHouse")
    @CachePut(key = "#id")
    public House saveHouse(Integer id, String houseName, String houseSize){
        House house = new House(id,houseName, houseSize);
        houseRepository.save(house);
        return house;
    }

    //http://localhost:8084/queryHouse?id=1
    // 方法中使用到了@Cacheable注解，这个注解在执行前先查看缓存中是不是已经存在了，如果存在，直接返回。如果不存在，将方法的返回值放入缓存
    @GetMapping("/queryHouse")
    @Cacheable(key = "#id")
    public House queryHouse(Integer id){
        House house = houseRepository.getOne(id);
        return house;
    }

    //http://localhost:8084/deleteHouse?id=1
    // 方法中使用到了@CacheEvict注解，这个注解在执行方法执行成功后会从缓存中移除
    @GetMapping("/deleteHouse")
    @CacheEvict(key = "#id")
    public String deleteHouse(Integer id){
        houseRepository.deleteById(id);
        return "success";
    }

    //http://localhost:8084/deleteCache
    // 这个方法的也是使用的@CacheEvict注解，不同的是使用了allEntries熟悉，默认为false，true的时候移除所有缓存。
    @GetMapping("/deleteCache")
    @CacheEvict(allEntries = true)
    public void deleteCache() {
    }
}
