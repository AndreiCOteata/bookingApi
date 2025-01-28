package com.example.application.company.hotel.room.dao.dto;

import com.example.application.common.model.AbstractDto;
import com.example.application.company.hotel.dao.dto.validator.NameConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomDto extends AbstractDto {
    private static final long serialVersionUID = -8689127738773284849L;

    //contain room description without long id
    //name, price, type, description
    @NameConstraint
    private String hotelName;

}

