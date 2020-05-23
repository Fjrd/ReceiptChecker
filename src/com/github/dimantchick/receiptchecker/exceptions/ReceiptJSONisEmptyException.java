package com.github.dimantchick.receiptchecker.exceptions;

import com.github.dimantchick.receiptchecker.Answer;

/**
 * Исключение при попытке получить Receipt до получения чека из ФНС
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class ReceiptJSONisEmptyException extends Exception {

    public ReceiptJSONisEmptyException() {
    }

    public ReceiptJSONisEmptyException(String message) {
        super(message);
    }

    public ReceiptJSONisEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiptJSONisEmptyException(Throwable cause) {
        super(cause);
    }

    public ReceiptJSONisEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
