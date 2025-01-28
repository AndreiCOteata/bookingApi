package com.example.application.company.hotel.room.dao.dto;

import com.example.application.company.hotel.room.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomConverterImpl implements RoomConverter{
    @Override
    public Room createFrom(RoomDto dto) {
        Room entity = new Room();
        updateEntity(entity, dto);
        return entity;

    }

    @Override
    public RoomDto createFrom(Room entity) {
        RoomDto roomDto = new RoomDto();
        roomDto.setHotelName(entity.getName());
        return roomDto;
    }

    @Override
    public Room updateEntity(Room entity, RoomDto dto) {
        if (entity != null & dto != null) {
            entity.setName(dto.getHotelName());
        }
        return entity;
    }
}
