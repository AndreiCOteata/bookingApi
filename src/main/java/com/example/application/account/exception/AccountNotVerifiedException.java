package com.example.application.account.exception;

public class AccountNotVerifiedException extends Exception{
    private static final long serialVersionUID = 198734921709183L;

    public AccountNotVerifiedException() {
        super();
    }

    public AccountNotVerifiedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccountNotVerifiedException(final String message) {
        super(message);
    }

    public AccountNotVerifiedException(final Throwable cause) {
        super(cause);
    }
}
