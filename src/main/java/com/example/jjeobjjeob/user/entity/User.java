package com.example.jjeobjjeob.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id @Column
    private Long id;
    @Column
    private String email;
    @Column
    private String userId;
    @Column
    private String name;
    @Column
    private String password;
}
