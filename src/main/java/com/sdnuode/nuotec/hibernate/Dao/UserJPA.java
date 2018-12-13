package com.sdnuode.nuotec.hibernate.Dao;


import com.sdnuode.nuotec.hibernate.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

//public interface UserJPA extends JpaRepository<User,Long>,JpaSpecificationExecutor<User>,Serializable {
public interface UserJPA extends JpaRepository<User,Long>{
}
