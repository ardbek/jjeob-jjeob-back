package com.fmap.group.entity;

import com.fmap.common.Entity.BaseEntity;
import com.fmap.user.entity.User;
import jakarta.persistence.*;

/**
 * 사용자 그룹 매핑 Entity
 */

@SequenceGenerator(
        name = "GROUPUSER_SEQ_GENERATOR",
        sequenceName = "GROUPUSER_SEQ"
)


@Entity
@Table(name = "TBL_GROUP_USER")
public class GroupUser extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "GROUPUSER_SEQ_GENERATOR"
    )
    @Column(name = "GROUPUSER_NO")
    private Long groupUserNo;

    @ManyToOne
    @JoinColumn(name = "GROUP_NO", referencedColumnName = "GROUP_NO")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "USER_NO", referencedColumnName = "USER_NO")
    private User user;

}
