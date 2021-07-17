package com.github.dimantchick.receiptchecker.exceptions;

import com.github.dimantchick.receiptchecker.Answer;

/**
 * Исключение некорректной пары телефон/пароль.
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class IncorrectPhonePassException extends Exception {
    private Answer answer;

    public IncorrectPhonePassException() {
    }

    public IncorrectPhonePassException(Answer answer) {
        this.answer = answer;
    }

    public IncorrectPhonePassException(String message) {
        super(message);
    }

    public IncorrectPhonePassException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPhonePassException(Throwable cause) {
        super(cause);
    }

    public IncorrectPhonePassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Answer getAnswer() {
        return answer;
    }
}
