package org.example.model;

public enum OrderStatus {
    ACCEPT(1),
    REJECT(2),
    EXPECTED(0);
    int number;

    OrderStatus(int number) {
        this.number = number;
    }
}
