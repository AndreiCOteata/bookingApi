package com.example.application.company.hotel.room;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomTypeEnum implements RoomTypeNumberOfPersons{

    SINGLE(1), DOUBLE(2), QUEEN(2), KIND(2), VILLA(4);

    private final int numberOfPersons;
}
