package com.example.application.company.hotel.room.dao;

import com.example.application.company.hotel.Hotel;
import com.example.application.company.hotel.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> getAllByHotel(Hotel hotel);

    default List<Room> getAllRooms(Hotel hotel){
        List<Room> rooms = getAllByHotel(hotel);
        if( rooms == null){
            return Collections.emptyList();
        }
        return rooms;
    }
}
