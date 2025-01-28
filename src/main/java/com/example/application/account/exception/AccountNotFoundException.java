package com.example.application.account.exception;

public class AccountNotFoundException extends Exception {

    private static final long serialVersionUID = -813576735279026961L;

    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(final String message) {
        super(message);
    }

    public AccountNotFoundException(final Throwable cause) {
        super(cause);
    }
}
