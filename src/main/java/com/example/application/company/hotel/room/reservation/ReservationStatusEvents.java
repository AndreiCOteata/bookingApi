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
@SequenceGenerator(name = "reservation_status_events_seq", sequenceName = "reservation_status_events_sequence", allocationSize = 1)
public class ReservationStatusEvents {

    private static final long serialVersionUID = 1226853008333307918L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_status_events_seq")
    private Long id;

    @OneToOne(mappedBy = "reservationStatus")
    private Reservation reservation;

    @Column(name = "details")
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, name = "status")
    private ReservationStatus status;
}
