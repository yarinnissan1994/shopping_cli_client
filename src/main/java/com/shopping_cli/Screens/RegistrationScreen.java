package com.shopping_cli.Screens;

import com.shopping_cli.services.ConsoleService;

import java.util.Scanner;

public class RegistrationScreen {
    public static void start(Scanner scanner) {
        ConsoleService.clearConsole();

        boolean validCredentials = false;

        do {
            ConsoleService.spacingConsole();

            System.out.println("==========================================");
            System.out.println("|              Shopping CLI              |");
            System.out.println("==========================================");
            System.out.println("|            Registration Page           |");
            System.out.println("==========================================");

            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();


            if (email.equals("") && password.equals("") && name.equals("")) {
                validCredentials = true;
            } else {
                ConsoleService.spacingConsole();
                System.out.println("Invalid credentials. Please try again.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            }

            System.out.println();
        } while (!validCredentials);
    }
}
