package com.example.application.location.country.dao.dto;

import com.example.application.common.model.AbstractDto;
import com.example.application.location.country.CountryEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CountryDto extends AbstractDto {

    private static final long serialVersionUID = 4479567475998958308L;

    private CountryEnum name;
}
