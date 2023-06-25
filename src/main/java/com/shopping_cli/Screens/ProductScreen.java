package com.shopping_cli.Screens;

import com.shopping_cli.data.CartData;
import com.shopping_cli.entities.OrderItem;
import com.shopping_cli.entities.Product;
import com.shopping_cli.services.ConsoleService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ProductScreen {
    public static void start(Scanner scanner, Product product) {
        ConsoleService.clearConsole();

        boolean breakLoop;

        do {
            breakLoop = false;

            System.out.println("==========================================");
            System.out.println("|             Shopping CLI               |");
            System.out.println("==========================================");
            System.out.println("|               Product                  |");
            System.out.println("==========================================");
            System.out.println("|  1. Choose amount                      |");
            System.out.println("|  2. Go to Cart                         |");
            System.out.println("|  0. Continue Shopping                  |");
            System.out.println("==========================================");

            System.out.println();
            System.out.println("Name: " + product.getName());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Price: " + product.getPrice() + "$");
            System.out.println("Units in stock: " + product.getQuantity());

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    addToCart(product, scanner);
                    breakLoop = true;
                }
                case "2" -> {
                    MyCartScreen.start(scanner);
                    breakLoop = true;
                }
                case "0" -> breakLoop = true;
                default -> {
                    ConsoleService.spacingConsole();
                    System.out.println("Invalid choice. Please try again.");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    ConsoleService.spacingConsole();
                }
            }

            System.out.println();
        } while (!breakLoop);
    }

    private static void addToCart(Product product, Scanner scanner) {
        int amount = product.getQuantity() + 1;

        while (amount > product.getQuantity()) {
            ConsoleService.spacingConsole();
            System.out.println("You can only order up to: " + product.getQuantity());
            System.out.print("Amount: ");
            amount = Integer.parseInt(scanner.nextLine());

            if (amount > product.getQuantity()) {
                System.out.println("Invalid amount. Please try again.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                ConsoleService.spacingConsole();
            }
        }

        if (amount > 0) {
            System.out.println("Details of your order: ");
            System.out.println("Product: " + product.getName());
            System.out.println("Amount: " + amount);
            System.out.println("Total price: " + amount * product.getPrice() + "$");
            System.out.print("Add to cart? (Press any key to confirm) - (N key to cancel): ");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("n")) {
                return;
            }

            try {
                CartData.addToCart(new OrderItem(product, amount, (amount * product.getPrice())));
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
