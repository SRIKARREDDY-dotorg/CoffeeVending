package org.parking.lot;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CoffeeVendingMachine coffeeVendingMachine = CoffeeVendingMachine.getInstance();
        coffeeVendingMachine.displayMenu();

        Coffee espresso = coffeeVendingMachine.selectCoffee("espresso");
        coffeeVendingMachine.dispenseCoffee(espresso, new Payment(3.0));

        Coffee latte = coffeeVendingMachine.selectCoffee("latte");
        coffeeVendingMachine.dispenseCoffee(latte, new Payment(3.4));

        Coffee cappuccino = coffeeVendingMachine.selectCoffee("cappuccino");
        coffeeVendingMachine.dispenseCoffee(cappuccino, new Payment(4.0));
    }
}