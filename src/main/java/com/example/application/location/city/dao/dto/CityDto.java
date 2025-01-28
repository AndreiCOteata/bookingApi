package com.example.application.location.city.dao.dto;

import com.example.application.common.model.AbstractDto;
import com.example.application.company.hotel.Hotel;
import com.example.application.location.city.dao.dto.validator.PostalCodeConstraint;
import com.example.application.location.country.Country;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CityDto extends AbstractDto {

    private static final long serialVersionUID = 7656695797780289360L;
    private String name;

    @PostalCodeConstraint
    private Long postalCode;

    private Country country;
    private List<Hotel> hotels;
}
