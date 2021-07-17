package com.github.dimantchick.receiptchecker.exceptions;

import com.github.dimantchick.receiptchecker.Answer;

/**
 * Исключение запроса чека. Чек не найден.
 * Чек может быть недействительным (введены неверные данные чека)
 * Чек мог быть еще не добавлен в базу (обычно не более 24 часов)
 * Также чек может быть не найден, если он был получен достаточно давно. ФНС не хранит информацию по чекам за все время.
 * На момент написания этой статьи ФНС хранила детальную информацию порядка 2-3 месяцев.
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class ReceiptNotFoundException extends Exception {
    private Answer answer;

    public ReceiptNotFoundException() {
    }

    public ReceiptNotFoundException(Answer answer) {
        this.answer = answer;
    }

    public ReceiptNotFoundException(String message) {
        super(message);
    }

    public ReceiptNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiptNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReceiptNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Answer getAnswer() {
        return answer;
    }
}
