package com.example.application.location.address.dao.dto;

import com.example.application.common.model.AbstractDto;
import com.example.application.location.address.dao.dto.validator.NumberConstraint;
import com.example.application.location.address.dao.dto.validator.StreetConstraint;
import com.example.application.location.city.City;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddressDto extends AbstractDto {

    private static final long serialVersionUID = -4801450195211956019L;

    @StreetConstraint
    private String street;

    @NumberConstraint
    private Long number;

    private City city;
}
