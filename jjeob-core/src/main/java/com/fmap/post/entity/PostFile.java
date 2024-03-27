package com.fmap.post.entity;


import com.fmap.common.Entity.BaseEntity;
import jakarta.persistence.*;

/**
 * 게시글 첨부파일 entity
 */

@SequenceGenerator(
        name = "POST_SEQ_GENERATOR",
        sequenceName = "POST_SEQ"
)

@Entity
@Table(name = "TBL_POSTFILE")
public class PostFile extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "POST_SEQ_GENERATOR"
    )
    @Column(name="POSTFILE_NO")
    private Long postFileNo;

    @ManyToOne
    @JoinColumn(name = "POST_NO")
    private Post post;

}
