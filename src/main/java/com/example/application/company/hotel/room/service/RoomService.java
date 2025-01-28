package com.example.application.company.hotel.room.service;

import com.example.application.company.hotel.Hotel;
import com.example.application.company.hotel.room.Room;
import com.example.application.company.hotel.room.dao.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository repository;

    @Autowired
    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> gellAllRoomsByHotel(Hotel hotel){
        return repository.getAllRooms(hotel);
    }
}
