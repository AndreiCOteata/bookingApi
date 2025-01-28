package com.example.application.account;

import com.example.application.account.profile.Profile;
import com.example.application.common.model.AbstractEntity;
import com.example.application.company.Company;
import com.example.application.company.hotel.room.reservation.Reservation;
import com.example.application.location.address.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@Entity(name = "Account")
@Table(name = "account", schema = "public")
@EqualsAndHashCode(callSuper = true)
public class Account extends AbstractEntity {

    private static final long serialVersionUID = -6076308026678412994L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservationList;

    @OneToOne(cascade = CascadeType.ALL, fetch =  FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;
}
