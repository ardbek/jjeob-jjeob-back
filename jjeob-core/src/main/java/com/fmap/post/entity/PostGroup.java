package com.fmap.post.entity;

import com.fmap.common.Entity.BaseEntity;
import com.fmap.group.entity.Group;
import jakarta.persistence.*;

/**
 * 게시글 그룹 매핑 Entity
 */

@SequenceGenerator(
        name = "POSTGROUP_SEQ_GENERATOR",
        sequenceName = "POSTGROUP_SEQ"
)

@Entity
@Table(name = "TBL_POSTGROUP")
public class PostGroup extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "POSTGROUP_SEQ_GENERATOR"
    )
    @Column(name="POSTGROUP_NO")
    private Long postGroupNo;

    @ManyToOne
    @JoinColumn(name = "POST_NO", referencedColumnName = "POST_NO")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "GROUP_NO", referencedColumnName = "GROUP_NO")
    private Group group;

}
