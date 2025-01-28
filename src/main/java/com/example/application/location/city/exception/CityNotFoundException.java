package com.example.application.location.city.exception;

public class CityNotFoundException extends Exception{

    private static final long serialVersionUID = -5960497456596964153L;

    public CityNotFoundException() {
        super();
    }

    public CityNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CityNotFoundException(final String message) {
        super(message);
    }

    public CityNotFoundException(final Throwable cause) {
        super(cause);
    }
}
