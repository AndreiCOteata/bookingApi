package com.example.application.location.city;

import com.example.application.common.model.AbstractEntity;
import com.example.application.company.hotel.Hotel;
import com.example.application.location.country.Country;
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
@Entity(name = "City")
@Table(name = "city", schema = "public")
@SequenceGenerator(name = "city_seq", sequenceName = "city_sequence", allocationSize = 1)
public class City extends AbstractEntity {

    private static final long serialVersionUID = 7756263538271632741L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "postal_code")
    private Long postalCode;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hotel> hotels;
}
