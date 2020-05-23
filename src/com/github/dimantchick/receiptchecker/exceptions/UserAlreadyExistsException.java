package com.github.dimantchick.receiptchecker.exceptions;

import com.github.dimantchick.receiptchecker.Answer;

/**
 * Исключение при регистрации. Данный номер телефона уже зарегистрирован. Используйте восстановление пароля.
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class UserAlreadyExistsException extends Exception {
    private Answer answer;

    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(Answer answer) {
        this.answer = answer;
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Answer getAnswer() {
        return answer;
    }
}
