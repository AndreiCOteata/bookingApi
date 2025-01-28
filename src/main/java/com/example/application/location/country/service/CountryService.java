package com.example.application.location.country.service;

import com.example.application.common.exceptions.ResourceNotFoundException;
import com.example.application.location.country.CountryEnum;
import com.example.application.location.country.dao.CountryRepository;
import com.example.application.location.country.dao.dto.CountryConverterImpl;
import com.example.application.location.country.dao.dto.CountryDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryConverterImpl countryConverterImpl;

    public CountryDto getCountryByName(CountryEnum name) {
        return countryRepository.findByName(name)
                .map(countryConverterImpl::createFrom)
                .orElseThrow(() -> new ResourceNotFoundException("Country with id" + name + "does not exist!"));
    }
}
