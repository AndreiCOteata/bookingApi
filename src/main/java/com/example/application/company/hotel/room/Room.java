package com.example.application.company.hotel.room;

import com.example.application.common.model.AbstractEntity;
import com.example.application.company.hotel.Hotel;
import com.example.application.company.hotel.room.reservation.Reservation;
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
@Entity(name = "Room")
@Table(name = "room", schema = "public")
public class Room extends AbstractEntity {

    private static final long serialVersionUID = -4565151713720472922L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Exclude
    @JsonIgnore
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_description_id", referencedColumnName = "id", nullable = false)
    private RoomDescription roomDescription;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservationList;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
}
