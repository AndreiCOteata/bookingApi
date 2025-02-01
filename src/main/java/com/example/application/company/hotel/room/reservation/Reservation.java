package com.example.application.company.hotel.room.reservation;

import com.example.application.account.Account;
import com.example.application.common.model.BaseModel;
import com.example.application.company.hotel.room.Room;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@ToString
@Entity(name = "Reservation")
@EqualsAndHashCode(callSuper = true)
@Table(name = "reservation", schema = "public")
@SequenceGenerator(name = "reservation_seq", sequenceName = "reservation_sequence", allocationSize = 1)
public class Reservation extends BaseModel {

    private static final long serialVersionUID = -3685078407901028982L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq")
    @ToString.Exclude
    @JsonIgnore
    private Long id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @Column(name = "number_rooms")
    private Long numberOfRooms;

    @OneToOne(cascade = CascadeType.ALL, fetch =  FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "reservation_status_id", referencedColumnName = "id")
    private ReservationStatusEvents reservationStatus;

}
