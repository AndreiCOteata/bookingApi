package com.example.application.common.email;

import com.example.application.location.address.Address;
import lombok.Data;

import java.util.Date;

@Data
public class BookingNotificationUser implements EmailMessage{

    private String hotelName;
    private long numberOfRooms;
    private Date checkin;
    private Date checkout;
    private String address;

    @Override
    public String getAsHtml(EmailMapper mapper) {
        return mapper.getBodyEmail(this);
    }

    @Override
    public String getSubject() {
        return "Reservation confirmation";
    }
}
