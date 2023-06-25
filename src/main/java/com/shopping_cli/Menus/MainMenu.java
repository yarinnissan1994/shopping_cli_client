package com.shopping_cli.Menus;

import com.shopping_cli.services.ConsoleService;

import java.util.Scanner;

public class MainMenu {
    public static int start(Scanner scanner) {
        ConsoleService.clearConsole();
        boolean breakLoop;
        int value;

        do {
            breakLoop = true;

            System.out.println("==========================================");
            System.out.println("|             Shopping CLI               |");
            System.out.println("==========================================");
            System.out.println("|               Main Menu                |");
            System.out.println("==========================================");
            System.out.println("|  1. Enter Shop - Categories            |");
            System.out.println("|  2. My Cart                            |");
            System.out.println("|  3. Checkout                           |");
            System.out.println("|  4. My Profile                         |");
            System.out.println("|  0. Logout                             |");
            System.out.println("==========================================");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> value = 1; // Enter Shop - Categories
                case "2" -> value = 2; // My Cart
                case "3" -> value = 3; // Checkout
                case "4" -> value = 4; // My Profile
                case "0" -> {
                    System.out.println("Logging out...");
                    return 0; // Logout
                }
                default -> {
                    ConsoleService.spacingConsole();
                    System.out.println("Invalid choice. Please try again.");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    ConsoleService.spacingConsole();
                    breakLoop = false;
                    value = -1; // Invalid choice. Return -1.
                }
            }

            System.out.println();
        } while (!breakLoop);

        return value;
    }
}
