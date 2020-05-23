package com.github.dimantchick.receiptchecker.exceptions;

import com.github.dimantchick.receiptchecker.Answer;

/**
 * Исключение на не описанную в спецификации ошибку.
 * Для выяснения причин следует разбирать объект answer.
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class UnknownErrorException extends Exception {
    private Answer answer;

    public UnknownErrorException() {
    }

    public UnknownErrorException(Answer answer) {
        this.answer = answer;
    }

    public UnknownErrorException(String message) {
        super(message);
    }

    public UnknownErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownErrorException(Throwable cause) {
        super(cause);
    }

    public UnknownErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Answer getAnswer() {
        return answer;
    }
}
