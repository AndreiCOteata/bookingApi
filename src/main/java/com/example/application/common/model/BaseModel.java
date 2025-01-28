package com.example.application.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel extends AbstractEntity{

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Column(name = "ts_created", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdTimestamp;

    @JsonIgnore
    @Column(name = "ts_updated", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedTimestamp;
}
