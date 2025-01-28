package com.example.application.company.hotel.api;

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
public class HotelsRequestDto  extends AbstractDto {
    private static final long serialVersionUID = 698067610764898212L;

    @NotNull
    @JsonProperty(value = "destination")
    private String destination;
    @NotNull
    @JsonProperty(value = "adults")
    private Integer adults;
    @JsonProperty(value = "children")
    private Integer children;
    @NotNull
    @JsonProperty(value = "rooms")
    private Integer rooms;
    @NotNull
    @JsonProperty(value = "checkIn")
    private Long checkIn;
    @NotNull
    @JsonProperty(value = "checkOut")
    private Long checkOut;
}
