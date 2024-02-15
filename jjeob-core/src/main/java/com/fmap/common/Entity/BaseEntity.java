package com.fmap.common.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(updatable = false)
    private LocalDateTime frstRegistDt;

    @Column(updatable = false)
    private String frstRegistId;

    @Column
    private LocalDateTime lastModifiedDt;

    @Column
    private String lastModifiedId;

    @PrePersist
    protected void onCreate() {
        this.frstRegistDt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDt = LocalDateTime.now();
    }
}
