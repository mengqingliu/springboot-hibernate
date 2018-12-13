package com.sdnuode.nuotec.hibernate.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName House
 * @Description TODO
 * @Author mengq
 * @Date 2018/12/13 15:16
 **/
@Entity
public class House implements Serializable {
    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 10)
    private String houseName;
    private String houseSize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(String houseSize) {
        this.houseSize = houseSize;
    }

    public House(String houseName, String houseSize) {
        this.houseName = houseName;
        this.houseSize = houseSize;
    }

    public House(int id,String houseName, String houseSize) {
        this.id = id;
        this.houseName = houseName;
        this.houseSize = houseSize;
    }

    public House() {
    }
}
