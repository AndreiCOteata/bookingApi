package com.example.application.location.country.dao.dto;

import com.example.application.location.country.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryConverterImpl {

    public CountryDto createFrom(Country entity) {
        CountryDto countryDto = new CountryDto();
        if (entity != null) {
            countryDto.setName(entity.getName());
        }
        return countryDto;
    }
}
