package com.example.application.common.email;

import com.example.application.location.address.Address;
import lombok.Data;

import java.util.Date;

@Data
public class BookingNotificationHotel implements EmailMessage{

    private String userEmail;
    private Long guests;
    private Date checkin;
    private Date checkout;

    @Override
    public String getAsHtml(EmailMapper mapper) {
        return mapper.getBodyEmail(this);
    }

    @Override
    public String getSubject() {
        return "New guests are comming";
    }
}
