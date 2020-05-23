package com.github.dimantchick.receiptchecker.receipt;

/**
 * Объект документа чека. Сгенерирован автоматическим генератором на основе JSON - представления чеков от серера
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class Document {
    private Receipt receipt;

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
