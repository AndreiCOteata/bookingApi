package com.example.application.company.hotel.api;

import com.example.application.common.model.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomsResponseDto extends AbstractDto {
    private static final long serialVersionUID = 21312312311570L;

    private String type;
    private Integer numberOfRooms;
}
