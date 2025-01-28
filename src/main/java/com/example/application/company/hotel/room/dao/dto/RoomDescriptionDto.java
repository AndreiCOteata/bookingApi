package com.example.application.company.hotel.room.dao.dto;

import com.example.application.common.model.AbstractDto;
import com.example.application.company.hotel.dao.dto.validator.DescriptionConstraint;
import com.example.application.company.hotel.room.RoomTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomDescriptionDto extends AbstractDto {

    private static final long serialVersionUID = -194762313607608072L;

    @NotBlank
    private RoomTypeEnum roomTypeEnum;

    @DescriptionConstraint
    private String description;

    @NotBlank
    private double price;

    @NonNull
    private Long number;
}
