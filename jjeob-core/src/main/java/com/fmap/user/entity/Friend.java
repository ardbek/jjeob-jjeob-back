package com.fmap.user.entity;


import com.fmap.common.Entity.BaseEntity;
import jakarta.persistence.*;

/**
 * 친구 entity
 */

@SequenceGenerator(
        name = "FRIEND_SEQ_GENERATOR",
        sequenceName = "FRIEND_SEQ"
)

@Entity
@Table(name = "TB_FRIEND")
public class Friend extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "FRIEND_SEQ_GENERATOR"
    )
    @Column(name = "FRIEND_NO")
    private Long friendNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FRIEND_USER_NO")
    private User friendUser;

}
