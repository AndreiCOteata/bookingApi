package com.example.application.company.hotel.room.reservation;

import com.example.application.company.hotel.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> getAllByRoom(Room room);

    default List<Reservation> getAllByRoomId(Room room){
        List<Reservation> reservations = getAllByRoom(room);
        if(room == null){
            return Collections.emptyList();
        }
        return reservations;
    }
}
