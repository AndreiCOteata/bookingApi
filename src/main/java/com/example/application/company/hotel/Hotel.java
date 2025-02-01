package com.example.application.company.hotel;

import com.example.application.common.model.AbstractEntity;
import com.example.application.company.Company;
import com.example.application.company.hotel.room.Room;
import com.example.application.location.city.City;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
@Entity(name = "Hotel")
@Table(name = "hotel", schema = "public")
@SequenceGenerator(name = "hotel_seq", sequenceName = "hotel_sequence", allocationSize = 1)
public class Hotel extends AbstractEntity {

    private static final long serialVersionUID = 6797172096409042266L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_seq")
    @ToString.Exclude
    @JsonIgnore
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel", fetch = FetchType.LAZY)
    private List<Room> rooms = new ArrayList<>();
}
