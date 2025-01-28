package com.example.application.company.hotel.dao.dto;

import com.example.application.common.model.AbstractDto;
import com.example.application.company.hotel.api.RoomsResponseDto;
import com.example.application.company.hotel.dao.dto.validator.DescriptionConstraint;
import com.example.application.company.hotel.dao.dto.validator.NameConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HotelDto extends AbstractDto {
    private static final long serialVersionUID = -649042277698707155L;

    @NotBlank
    @NameConstraint
    private String name;

    @NonNull
    @DescriptionConstraint
    private String description;

    @NonNull
    private Boolean isActive;

    private String city;

    private String country;

    private List<RoomsResponseDto> rooms;
}

