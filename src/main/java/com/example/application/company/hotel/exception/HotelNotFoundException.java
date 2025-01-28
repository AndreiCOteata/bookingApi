package com.example.application.company.hotel.exception;

public class HotelNotFoundException extends Exception{

    private static final long serialVersionUID = -2949835879292400456L;

    public HotelNotFoundException() {
        super();
    }

    public HotelNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public HotelNotFoundException(final String message) {
        super(message);
    }

    public HotelNotFoundException(final Throwable cause) {
        super(cause);
    }
}
