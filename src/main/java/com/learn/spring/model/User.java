package com.learn.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Entity
@Table
@NoArgsConstructor
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "profile_name")
    private String profileName;
    private String email;
    private String phone;
    private String password;

//    @Column(name = "websocket_id")
//    private String websocketId;

    private Boolean deleted;

    public User(String fN, String lN, String pN, String ph, String em,String pwd) {
        firstName=fN;
        lastName=lN;
        profileName=pN;
        phone=ph;
        email=em;
        password=pwd;
        deleted=false;
    }
}
