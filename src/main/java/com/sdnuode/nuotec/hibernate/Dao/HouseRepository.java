package com.sdnuode.nuotec.hibernate.Dao;

import com.sdnuode.nuotec.hibernate.Entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName HouseRepository
 * @Description TODO
 * @Author mengq
 * @Date 2018/12/13 15:18
 **/
public interface HouseRepository extends JpaRepository<House,Integer> {
}
