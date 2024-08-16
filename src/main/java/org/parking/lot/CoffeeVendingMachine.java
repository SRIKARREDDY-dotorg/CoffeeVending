package org.parking.lot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CoffeeVendingMachine {
    private static final CoffeeVendingMachine instance = new CoffeeVendingMachine();
    private final List<Coffee> coffeeMenu;
    private final Map<String, Ingredient> ingredients;

    private CoffeeVendingMachine() {
        coffeeMenu = new ArrayList<>();
        ingredients = new ConcurrentHashMap<>();
        initializeIngredients();
        initializeCoffeeMenu();
    }

    public static CoffeeVendingMachine getInstance() {
        return instance;
    }

    private void initializeIngredients() {
        // Initialize ingredients with available quantities
        ingredients.put("Coffee", new Ingredient("Coffee", 10));
        ingredients.put("Milk", new Ingredient("Milk", 10));
        ingredients.put("Water", new Ingredient("Water", 10));
    }

    private void initializeCoffeeMenu() {
        // Initialize coffee menu with available coffees
        final Map<Ingredient, Integer> espressoRecipe = new ConcurrentHashMap<>();
        espressoRecipe.put(ingredients.get("Coffee"), 1);
        espressoRecipe.put(ingredients.get("Water"), 1);
        coffeeMenu.add(new Coffee("espresso", 2.5, espressoRecipe));

        final Map<Ingredient, Integer> cappuccinoRecipe = new ConcurrentHashMap<>();
        cappuccinoRecipe.put(ingredients.get("Coffee"), 1);
        cappuccinoRecipe.put(ingredients.get("Milk"), 1);
        cappuccinoRecipe.put(ingredients.get("Water"), 1);
        coffeeMenu.add(new Coffee("cappuccino", 3.5, cappuccinoRecipe));

        final Map<Ingredient, Integer> latteRecipe = new ConcurrentHashMap<>();
        latteRecipe.put(ingredients.get("Coffee"), 1);
        latteRecipe.put(ingredients.get("Milk"), 2);
        latteRecipe.put(ingredients.get("Water"), 1);
        coffeeMenu.add(new Coffee("latte", 4.0, latteRecipe));
    }

    public void displayMenu() {
        System.out.println("Coffee Menu:");
        for (Coffee coffee : coffeeMenu) {
            System.out.println(coffee.getName() + " - $" + coffee.getPrice());
        }
    }

    public synchronized Coffee selectCoffee(String name) {
        for (Coffee coffee : coffeeMenu) {
            if (coffee.getName().equalsIgnoreCase(name)) {
                return coffee;
            }
        }
        return null;
    }

    public void dispenseCoffee(Coffee coffee, Payment payment) {
        if(coffee.getPrice() <= payment.getAmount()) {
            if(hasSufficientIngredients(coffee)) {
                updateIngredients(coffee);
                System.out.println("Dispensing " + coffee.getName());
                double extraAmount = payment.getAmount() - coffee.getPrice();
                if(extraAmount > 0) {
                    System.out.println("Returning change: $" + extraAmount);
                }
            } else {
                System.out.println("Insufficient ingredients for " + coffee.getName());
            }
        } else {
            System.out.println("Insufficient payment for " + coffee.getName());
        }
    }

    private boolean hasSufficientIngredients(Coffee coffee) {
        for(Map.Entry<Ingredient, Integer> entry : coffee.getRecipe().entrySet()) {
            if (entry.getValue() > entry.getKey().getQuantity()) {
                return false;
            }
        }
        return true;
    }

    private void updateIngredients(Coffee coffee) {
        for(Map.Entry<Ingredient, Integer> entry : coffee.getRecipe().entrySet()) {
            entry.getKey().updateQuantity(-entry.getValue());
            if(entry.getKey().getQuantity() < 3) {
                // Notify low stock
                System.out.println("Low inventory alert: " + entry.getKey().getName());
            }
        }
    }

}
