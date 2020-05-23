package com.github.dimantchick.receiptchecker.receipt;

/**
 * Один товар из чека. Сгенерирован автоматическим генератором на основе JSON - представления чеков от серера
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class Item {
    private int sum;
    private int price;
    private String name;
    private int quantity;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
