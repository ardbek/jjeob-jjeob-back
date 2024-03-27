package com.fmap.user.entity;


import jakarta.persistence.*;

/**
 * 친구 entity
 */

@SequenceGenerator(
        name = "FRIEND_SEQ_GENERATOR",
        sequenceName = "FRIEND_SEQ"
)

@Entity
public class Friend {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "FRIEND_SEQ_GENERATOR"
    )
    @Column(name = "FRIEND_NO")
    private Long friendNo;

    @ManyToOne
    @JoinColumn(name = "USER_NO")
    private User user;

    @ManyToOne
    @JoinColumn(name = "FRIEND_USER_NO")
    private User friendUser;

}
