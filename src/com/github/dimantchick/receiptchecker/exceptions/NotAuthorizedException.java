package com.github.dimantchick.receiptchecker.exceptions;

/**
 * Исключение запроса без авторизации. (billChecker.user == null)
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class NotAuthorizedException extends Exception {
    public NotAuthorizedException() {
    }

    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthorizedException(Throwable cause) {
        super(cause);
    }

    public NotAuthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
