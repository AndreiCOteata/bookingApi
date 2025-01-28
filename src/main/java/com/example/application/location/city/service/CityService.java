package com.example.application.location.city.service;

import com.example.application.common.exceptions.ResourceNotFoundException;
import com.example.application.location.city.dao.dto.CityConverterImpl;
import com.example.application.location.city.City;
import com.example.application.location.city.dao.CityRepository;
import com.example.application.location.city.dao.dto.CityDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CityService {

    private final CityRepository cityRepository;
    private final CityConverterImpl cityConverterImpl;

    public CityDto getCityById(Long id) {
        return cityRepository
                .findById(id)
                .map(cityConverterImpl::createFrom)
                .orElseThrow(() -> new ResourceNotFoundException("City with id " + id + " does not exist!"));
    }

    public CityDto updateCity(Long id, CityDto cityDto) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found!"));
        cityRepository.save(cityConverterImpl.updateEntity(city,cityDto));
        return cityConverterImpl.createFrom(city);
    }

    public City getByName(String name) {
        return cityRepository.getByName(name);
    }
}
