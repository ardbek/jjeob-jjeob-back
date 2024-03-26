package com.fmap.post.entity;

import com.fmap.user.entity.User;
import jakarta.persistence.*;

@SequenceGenerator(
        name = "POST_SEQ_GENERATOR",
        sequenceName = "POST_SEQ"
)

@Entity
public class LikePost {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "POST_SEQ_GENERATOR"
    )
    private Long likePostNo;

    @ManyToOne
    @JoinColumn(name = "USER_NO")
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_NO")
    private Post post;

}
