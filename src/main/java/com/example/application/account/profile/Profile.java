package com.example.application.account.profile;

import com.example.application.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString
@Entity(name = "Profile")
@Table(name = "profile", schema = "public")
public class Profile {

    private static final long serialVersionUID = -6076308026678412994L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @OneToOne(mappedBy = "profile")
    private Account account;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @JsonIgnore
    @Column(name = "last_failed_login_time")
    private Timestamp lastFailedLoginTime;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "language")
    private String language;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(name = "role_profile",
    joinColumns = @JoinColumn(name = "profile_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Role> roleList;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdTimestamp;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedTimestamp;
}
