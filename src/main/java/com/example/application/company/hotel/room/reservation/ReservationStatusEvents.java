package com.example.application.company.hotel.room.reservation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString
@Entity(name = "ReservationStatusEvents")
@Table(name = "reservation_status_events", schema = "public")
public class ReservationStatusEvents {

    private static final long serialVersionUID = 1226853008333307918L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "reservationStatus")
    private Reservation reservation;

    @Column(name = "details")
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, name = "status")
    private ReservationStatus status;
}
