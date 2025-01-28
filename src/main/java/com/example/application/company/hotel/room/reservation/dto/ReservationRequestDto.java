package com.example.application.company.hotel.room.reservation.dto;

import com.example.application.common.model.AbstractDto;
import com.example.application.company.hotel.room.dao.dto.RoomRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto extends AbstractDto {

    private static final long serialVersionUID = 69131242148191570L;

    @NotNull
    @JsonProperty(value = "email")
    private String email;
    @NotNull
    @Size(min = 4, max = 30)
    @JsonProperty(value = "hotelName")
    private String hotelName;
    @NotNull
    @JsonProperty(value = "roomsList")
    private List<RoomRequestDto> roomDtoList;
    @NotNull
    @JsonProperty(value = "guests")
    private Long guests;
    @NotNull
    @JsonProperty(value = "checkIn")
    private Long checkIn;
    @NotNull
    @JsonProperty(value = "checkOut")
    private Long checkOut;
    @JsonProperty(value = "details")
    private String details;

}
