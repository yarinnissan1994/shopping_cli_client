package com.shopping_cli.Menus;

import com.shopping_cli.services.ConsoleService;

import java.util.Scanner;

public class EntryMenu {
    public static int start(Scanner scanner) {

        ConsoleService.clearConsole();

        boolean breakLoop;

        int value;

        do {
            breakLoop = true;

            System.out.println("==========================================");
            System.out.println("|        Welcome to Shopping CLI         |");
            System.out.println("==========================================");
            System.out.println("|              Entry Menu                |");
            System.out.println("==========================================");
            System.out.println("|  1. Register                           |");
            System.out.println("|  2. Login                              |");
            System.out.println("|  0. Exit                               |");
            System.out.println("==========================================");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

             value = switch (choice) {
                case "1" -> 1; // Register
                case "2" -> 2; // Login
                case "0" -> {
                    System.out.println("Exiting...");
                    yield 0;
                } // Exit
                default -> {
                    ConsoleService.spacingConsole();
                    System.out.println("Invalid choice. Please try again.");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    ConsoleService.spacingConsole();
                    breakLoop = false;
                    yield -1; // Invalid choice. Return -1.
                }
            };

            System.out.println();
        } while (!breakLoop);

        return value;
    }
}
