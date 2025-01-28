package com.example.application.company.hotel.room.dao.dto;

import com.example.application.company.hotel.room.RoomDescription;
import org.springframework.stereotype.Component;

@Component
public class RoomDescriptionConverterImpl implements RoomDescriptionConverter{
    @Override
    public RoomDescription createFrom(RoomDescriptionDto dto) {
        RoomDescription entity = new RoomDescription();
        updateEntity(entity, dto);
        return entity;
    }

    @Override
    public RoomDescriptionDto createFrom(RoomDescription entity) {
        RoomDescriptionDto roomDescriptionDto = new RoomDescriptionDto();
        roomDescriptionDto.setDescription(entity.getDescription());
        roomDescriptionDto.setPrice(entity.getPrice());
        roomDescriptionDto.setNumber(entity.getNumberOfRooms());
        return roomDescriptionDto;
    }

    @Override
    public RoomDescription updateEntity(RoomDescription entity, RoomDescriptionDto dto) {
        if(entity != null & dto != null) {
            entity.setDescription(dto.getDescription());
            entity.setPrice(dto.getPrice());
            entity.setNumberOfRooms(dto.getNumber());
        }
        return entity;
    }
}
