package org.parking.lot;

public class Payment {
    private final double amount;

    Payment(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
