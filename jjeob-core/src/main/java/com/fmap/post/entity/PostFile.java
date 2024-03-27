package com.fmap.post.entity;


import jakarta.persistence.*;

/**
 * 게시글 첨부파일 entity
 */

@SequenceGenerator(
        name = "POST_SEQ_GENERATOR",
        sequenceName = "POST_SEQ"
)

public class PostFile {
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
