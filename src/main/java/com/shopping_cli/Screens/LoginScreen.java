package com.shopping_cli.Screens;

import com.shopping_cli.services.ConsoleService;

import java.util.Scanner;

public class LoginScreen {
    public static void start(Scanner scanner) {

        boolean loggedIn = false;

        do {
            ConsoleService.clearConsole();

            System.out.println("==========================================");
            System.out.println("|              Shopping CLI              |");
            System.out.println("==========================================");
            System.out.println("|               Login Page               |");
            System.out.println("==========================================");

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            if (email.equals("") && password.equals("")) {
                loggedIn = true;
            } else {
                ConsoleService.clearConsole();
                System.out.println("Invalid credentials. Please try again.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            }

            System.out.println();
        } while (!loggedIn);
    }
}
