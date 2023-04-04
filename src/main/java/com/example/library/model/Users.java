package com.example.library.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(generator = "userSeq")
    @SequenceGenerator(name = "userSeq",sequenceName = "userSequence",allocationSize = 1,initialValue = 1)
    private Integer id;
    private String username;
    private String email;
    private String phoneNumber;
    private String passportId;
    private String password;
    private Boolean enabled;
    @Column(columnDefinition = "text default 'USER'")
    private String role = "USER";
//    private String role = "USER";
    private short isActive;

}
