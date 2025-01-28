package com.example.application.company.hotel.room.dao.dto;

import com.example.application.common.model.AbstractDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDto extends AbstractDto {
    private static final long serialVersionUID = 698067612131231915L;

    @NotNull
    @JsonProperty(value = "type")
    private String type;
    @NotNull
    @JsonProperty(value = "numberOfRooms")
    private Long numberOfRooms;
}
