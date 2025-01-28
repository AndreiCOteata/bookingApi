package com.example.application.account.profile;

import com.example.application.common.model.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
@Entity(name = "Role")
@Table(name = "role", schema = "public")
public class Role extends AbstractEntity {

    private static final long serialVersionUID = 5402950313606264066L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, name = "name")
    private RoleEnum name;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "roleList", cascade = CascadeType.ALL)
    private List<Profile> profileList;
}
