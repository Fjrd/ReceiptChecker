package com.github.dimantchick.receiptchecker.exceptions;

import com.github.dimantchick.receiptchecker.Answer;

/**
 * Исключение некорректного формата почты. Проверьте, что почта соответствует стандартному формату email
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class IncorrectEmailFormatException extends Exception {
    private Answer answer;

    public IncorrectEmailFormatException() {
    }

    public IncorrectEmailFormatException(Answer answer) {
        this.answer = answer;
    }

    public IncorrectEmailFormatException(String message) {
        super(message);
    }

    public IncorrectEmailFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectEmailFormatException(Throwable cause) {
        super(cause);
    }

    public IncorrectEmailFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Answer getAnswer() {
        return answer;
    }
}
