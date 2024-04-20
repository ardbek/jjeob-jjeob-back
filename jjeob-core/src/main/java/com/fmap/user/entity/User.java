package com.fmap.user.entity;

import com.fmap.common.Entity.BaseEntity;
import com.fmap.post.entity.LikePost;
import com.fmap.post.entity.Post;
import jakarta.persistence.*;
import lombok.Builder;
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
@Table(name = "TBL_USERS")
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

    @Builder
    public User(String userId, String email, String password, String name) {
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.password = password;
    }


}
