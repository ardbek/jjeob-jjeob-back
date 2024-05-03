package com.fmap.post.entity;

import com.fmap.common.Entity.BaseEntity;
import com.fmap.restaurant.entity.Restaurant;
import com.fmap.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시글 Entity
 */

@SequenceGenerator(
        name = "POST_SEQ_GENERATOR",
        sequenceName = "POST_SEQ"
)

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_POST")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "POST_SEQ_GENERATOR"
    )
    @Column(name="POST_NO")
    private Long postNo; // PK
    @Column private String rating; //평점
    @Column private String category; //카테고리
    @Column private String price; //가격대
    @Column private String content; // 후기내용
    @Column private String visitDate; // 방문일
    @Column private String visitorCount; // 방문자 수
    @Column private String visibleYn; // 글 노출 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO", referencedColumnName = "USER_NO")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RSTNT_NO", referencedColumnName = "RSTNT_NO")
    private Restaurant restaurant;

}
