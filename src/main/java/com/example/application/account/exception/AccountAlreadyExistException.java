package com.example.application.account.exception;

public class AccountAlreadyExistException extends Exception{
    private static final long serialVersionUID = 5861310537366287163L;

    public AccountAlreadyExistException() {
        super();
    }

    public AccountAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccountAlreadyExistException(final String message) {
        super(message);
    }

    public AccountAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
