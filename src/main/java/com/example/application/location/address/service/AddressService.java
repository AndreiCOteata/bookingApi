package com.example.application.location.address.service;

import com.example.application.common.exceptions.ResourceNotFoundException;
import com.example.application.location.address.Address;
import com.example.application.location.address.dao.AddressRepository;
import com.example.application.location.address.dao.dto.AddressConverterImpl;
import com.example.application.location.address.dao.dto.AddressDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressConverterImpl addressConverterImpl;

    public AddressDto getAddressById(Long id){
        return addressRepository
                .findById(id)
                .map(addressConverterImpl::createFrom)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id " + id + " does not exist!"));
    }

    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Address not found!"));
        addressRepository.save(addressConverterImpl.updateEntity(address,addressDto));
        return addressConverterImpl.createFrom(address);
    }
}
