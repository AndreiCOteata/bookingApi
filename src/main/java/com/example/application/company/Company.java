package com.example.application.company;

import com.example.application.account.Account;
import com.example.application.common.model.AbstractEntity;
import com.example.application.company.hotel.Hotel;
import com.example.application.company.plan.Plan;
import com.example.application.location.city.City;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(force = true)
@ToString
@Entity(name = "Company")
@Table(name = "company", schema = "public")
@SequenceGenerator(name = "company_seq", sequenceName = "company_sequence", allocationSize = 1)
public class Company extends AbstractEntity {

    private static final long serialVersionUID = -4926622510214183768L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    @ToString.Exclude
    @JsonIgnore
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vat_id", unique = true)
    private String vatId;

    @NonNull
    @Column(name = "email", unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Column(name = "details")
    private String details;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToOne(mappedBy = "company")
    private Account account;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Plan> planList;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hotel> hotels;
}
