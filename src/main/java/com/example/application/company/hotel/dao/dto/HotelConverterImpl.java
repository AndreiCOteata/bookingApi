package com.example.application.company.hotel.dao.dto;

import com.example.application.company.hotel.Hotel;
import org.springframework.stereotype.Component;

@Component
public class HotelConverterImpl implements HotelConverter {
    @Override
    public Hotel createFrom(HotelDto dto) {
        Hotel entity = new Hotel();
        updateEntity(entity, dto);
        entity.setIsActive(true);
        return entity;
    }

    @Override
    public HotelDto createFrom(Hotel entity) {
        HotelDto hotelDto = new HotelDto();
        hotelDto.setName(entity.getName());
        hotelDto.setDescription(entity.getDescription());
        hotelDto.setIsActive(entity.getIsActive());
        hotelDto.setCity(entity.getCity().getName());
        hotelDto.setCountry(entity.getCity().getCountry().getName().toString());
        return hotelDto;
    }

    @Override
    public Hotel updateEntity(Hotel entity, HotelDto dto) {
        if (entity != null && dto != null) {
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setIsActive(dto.getIsActive());
        }
        return entity;
    }
}
