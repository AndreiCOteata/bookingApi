package com.example.application.common.dtos;

import com.example.application.account.dao.dto.validator.EmailConstraint;
import com.example.application.common.model.AbstractDto;
import com.example.application.location.address.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEmailDto extends AbstractDto {
    private static final long serialVersionUID = 6980676104695616323L;

    @NotNull
    @JsonProperty(value = "hotelName")
    private String hotelName;

    @NotNull
    private Long numbersOfRooms;

    @NotNull
    private Date checkin;

    @NotNull
    private Date checkout;

    @EmailConstraint
    @NotNull
    @JsonProperty(value = "userEmail")
    private String userEmail;

    @NotNull
    private Long guests;

    @NotNull
    private String address;

    @EmailConstraint
    @NotNull
    @JsonProperty(value = "userEmail")
    private String hotelEmail;

}
