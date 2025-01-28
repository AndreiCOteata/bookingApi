package com.example.application.company.plan;

import com.example.application.common.model.BaseModel;
import com.example.application.company.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;


@Data
@NoArgsConstructor
@ToString
@Entity (name = "plan")
@Table(name = "plan", schema = "public")
@EqualsAndHashCode(callSuper = true)
public class Plan extends BaseModel {

    private static final long serialVersionUID = 4495030777366472474L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Exclude
    @JsonIgnore
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @Column(name = "rooms")
    private Long rooms;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;
}
