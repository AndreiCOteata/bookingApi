package com.example.application.company.hotel.room;

import com.example.application.common.model.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
@Entity(name = "RoomDescription")
@Table(name = "room_description", schema = "public")
public class RoomDescription extends AbstractEntity {

    private static final long serialVersionUID = -5723739010704911501L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Exclude
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_type_id", referencedColumnName = "id", nullable = false)
    private RoomType room_type;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "number_of_rooms")
    private Long numberOfRooms;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "roomDescription")
    private Room room;
}
