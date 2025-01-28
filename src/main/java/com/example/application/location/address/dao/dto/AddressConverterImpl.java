package com.example.application.location.address.dao.dto;

import com.example.application.location.address.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressConverterImpl implements AddressConverter {

    @Override
    public Address createFrom(AddressDto dto) {
        Address entity = new Address();
        updateEntity(entity, dto);
        return entity;
    }

    @Override
    public AddressDto createFrom(Address entity) {
        AddressDto addressDto = new AddressDto();
        if (entity != null) {
            addressDto.setNumber(entity.getNumber());
            addressDto.setStreet(entity.getStreet());
            addressDto.setCity(entity.getCity());
        }
        return addressDto;
    }

    @Override
    public Address updateEntity(Address entity, AddressDto dto) {
        if (entity != null && dto != null) {
            entity.setNumber(dto.getNumber());
            entity.setStreet(dto.getStreet());
            entity.setCity(dto.getCity());
        }
        return entity;
    }
}
