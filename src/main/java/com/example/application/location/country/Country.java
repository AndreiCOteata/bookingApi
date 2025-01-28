package com.example.application.location.country;

import com.example.application.common.model.AbstractEntity;
import com.example.application.location.city.City;
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
@Entity(name = "Country")
@Table(name = "country", schema = "public")
public class Country extends AbstractEntity {

    private static final long serialVersionUID = 2589683683069679663L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, name = "name")
    private CountryEnum name;

    @OneToMany(mappedBy = "country")
    private List<City> cityList;
}
