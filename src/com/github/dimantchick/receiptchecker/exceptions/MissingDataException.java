package com.github.dimantchick.receiptchecker.exceptions;

import com.github.dimantchick.receiptchecker.Answer;

/**
 * Исключение неправильные и/или отсутствуют параметры чека (дата, сумма и т.д.)
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class MissingDataException extends Exception {
    private Answer answer;

    public MissingDataException() {
    }

    public MissingDataException(Answer answer) {
        this.answer = answer;
    }

    public MissingDataException(String message) {
        super(message);
    }

    public MissingDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingDataException(Throwable cause) {
        super(cause);
    }

    public MissingDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Answer getAnswer() {
        return answer;
    }
}
