package com.sdnuode.nuotec.hibernate.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="user")
public class User {
    @Id
//    @GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private String age;

    @Column(name = "address")
    private String address;
}
