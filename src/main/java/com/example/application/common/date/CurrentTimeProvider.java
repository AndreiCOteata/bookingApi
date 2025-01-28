package com.example.application.common.date;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class CurrentTimeProvider {
    public Timestamp getTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public Instant getInstant(){
        return Instant.now();
    }

    public Date getDate(Long date){return new Date(date);}
}
