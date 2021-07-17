package com.github.dimantchick.receiptchecker.exceptions;

import com.github.dimantchick.receiptchecker.Answer;

/**
 * Исключение при восстановлении пароля - используйте регистрацию
 * или
 * Исключение при получении чека - неудачная авторизация (проверьте телефон/пароль)
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class UserNotFoundException extends Exception {
    private Answer answer;

    public UserNotFoundException() {
    }

    public UserNotFoundException(Answer answer) {
        this.answer = answer;
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Answer getAnswer() {
        return answer;
    }
}
