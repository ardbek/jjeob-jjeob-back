package com.example.jjeobjjeob.user.entity;

import com.example.jjeobjjeob.common.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        sequenceName = "USER_SEQ"
)
@Getter
@Setter
public class User extends BaseEntity {


    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "USER_SEQ_GENERATOR"
    )
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
