package com.fmap.group.entity;

import com.fmap.common.Entity.BaseEntity;
import com.fmap.user.entity.User;
import jakarta.persistence.*;

import java.util.List;

/**
 * 사용자 그룹 Entity
 */

@SequenceGenerator(
        name = "GROUP_SEQ_GENERATOR",
        sequenceName = "GROUP_SEQ"
)
@Entity
@Table(name = "TB_GROUP")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "GROUP_SEQ_GENERATOR"
    )
    @Column(name="GROUP_NO")
    private Long groupNo; // pk

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO", referencedColumnName = "USER_NO")
    private User user;

    @OneToMany(mappedBy = "group")
    private List<GroupUser> groupUsers;


}
