package com.github.dimantchick.receiptchecker.exceptions;

import com.github.dimantchick.receiptchecker.Answer;

/**
 * Исключение некорректного формата телефона. Телефон должен быть в формате +79*********
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class IncorrectPhoneNumberException extends Exception {
    private Answer answer;

    public IncorrectPhoneNumberException() {
    }

    public IncorrectPhoneNumberException(Answer answer) {
        this.answer = answer;
    }

    public IncorrectPhoneNumberException(String message) {
        super(message);
    }

    public IncorrectPhoneNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPhoneNumberException(Throwable cause) {
        super(cause);
    }

    public IncorrectPhoneNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Answer getAnswer() {
        return answer;
    }
}
