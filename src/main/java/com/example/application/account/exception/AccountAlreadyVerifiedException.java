package com.example.application.account.exception;

public class AccountAlreadyVerifiedException extends Exception{
    private static final long serialVersionUID = 98179816983491823L;

    public AccountAlreadyVerifiedException() {
        super();
    }

    public AccountAlreadyVerifiedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccountAlreadyVerifiedException(final String message) {
        super(message);
    }

    public AccountAlreadyVerifiedException(final Throwable cause) {
        super(cause);
    }
}
