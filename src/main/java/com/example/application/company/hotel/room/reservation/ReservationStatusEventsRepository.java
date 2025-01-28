package com.example.application.company.hotel.room.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationStatusEventsRepository extends JpaRepository<ReservationStatusEvents,Long> {
}
