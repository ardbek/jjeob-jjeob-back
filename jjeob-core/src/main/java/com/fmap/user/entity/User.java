package com.fmap.user.entity;

import com.fmap.common.Entity.BaseEntity;
import com.fmap.post.entity.LikePost;
import com.fmap.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 사용자 Entity
 */

@Entity
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        sequenceName = "USER_SEQ"
)
@Getter
@Setter
@Table(name = "USERS")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "USER_SEQ_GENERATOR"
    )
    @Column(name = "USER_NO")
    private Long userNo;
    @Column private String email;
    @Column private String userId;
    @Column private String name;
    @Column private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<LikePost> likePosts;
}
