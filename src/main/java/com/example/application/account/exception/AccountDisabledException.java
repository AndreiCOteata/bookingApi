package com.example.application.account.exception;

public class AccountDisabledException extends Exception{
    private static final long serialVersionUID = 3700545186274860872L;

    public AccountDisabledException() {
        super();
    }

    public AccountDisabledException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccountDisabledException(final String message) {
        super(message);
    }

    public AccountDisabledException(final Throwable cause) {
        super(cause);
    }
}
