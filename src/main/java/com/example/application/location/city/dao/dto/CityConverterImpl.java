package com.example.application.location.city.dao.dto;

import com.example.application.location.city.City;
import org.springframework.stereotype.Component;

@Component
public class CityConverterImpl implements CityConverter {
    @Override
    public City createFrom(CityDto dto) {
        City entity = new City();
        updateEntity(entity, dto);
        return entity;
    }

    @Override
    public CityDto createFrom(City entity) {
        CityDto cityDto = new CityDto();
        if (entity != null) {
            cityDto.setName(entity.getName());
            cityDto.setPostalCode(entity.getPostalCode());
            cityDto.setCountry(entity.getCountry());
            cityDto.setHotels(entity.getHotels());
        }
        return cityDto;
    }

    @Override
    public City updateEntity(City entity, CityDto dto) {
        if (entity != null & dto != null) {
            entity.setName(dto.getName());
            entity.setPostalCode(dto.getPostalCode());
            entity.setCountry(dto.getCountry());
            entity.setHotels(dto.getHotels());
        }
        return entity;
    }
}
