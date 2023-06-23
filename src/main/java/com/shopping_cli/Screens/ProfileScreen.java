package com.shopping_cli.Screens;

import com.shopping_cli.services.ConsoleService;

import java.util.Scanner;

public class ProfileScreen {
    public static int start(Scanner scanner) {

        ConsoleService.clearConsole();

        boolean breakLoop;

        int value;

        do {
            breakLoop = true;

            System.out.println("==========================================");
            System.out.println("|             Shopping CLI               |");
            System.out.println("==========================================");
            System.out.println("|              Entry Menu                |");
            System.out.println("==========================================");
            System.out.println("|  1. Edit Profile                       |");
            System.out.println("|  0. Back to Main Menu                  |");
            System.out.println("==========================================");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            value = switch (choice) {
                case "1" -> 1; // Edit Profile
                case "0" -> 0; // Back to Main Menu
                default -> {
                    ConsoleService.spacingConsole();
                    System.out.println("Invalid choice. Please try again.");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    ConsoleService.spacingConsole();
                    breakLoop = false;
                    yield 0;
                }
            };

            System.out.println();
        } while (!breakLoop);

        return value;
    }
}
