package com.fmap.post.entity;

import com.fmap.common.Entity.BaseEntity;
import com.fmap.user.entity.User;
import jakarta.persistence.*;

@SequenceGenerator(
        name = "POST_SEQ_GENERATOR",
        sequenceName = "POST_SEQ"
)

@Entity
@Table(name = "TB_LIKE_POST")
public class LikePost extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "POST_SEQ_GENERATOR"
    )
    @Column(name = "LIKEPOST_NO")
    private Long likePostNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_NO")
    private Post post;

}
