package com.shopping_cli.Screens;

import com.shopping_cli.Application;
import com.shopping_cli.entities.User;
import com.shopping_cli.entities.UserType;
import com.shopping_cli.services.ConsoleService;
import com.shopping_cli.data.UserData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class RegistrationScreen {
    public static void start(Scanner scanner) {

        boolean breakLoop = false;
        String choice;

        do {
            ConsoleService.clearConsole();

            System.out.println("==========================================");
            System.out.println("|              Shopping CLI              |");
            System.out.println("==========================================");
            System.out.println("|            Registration Page           |");
            System.out.println("==========================================");
            System.out.println();
            System.out.print("Press Enter to continue registration or type 0 to exit: ");

            choice = scanner.nextLine();

            if(choice.equals("0")) return;

            ConsoleService.spacingConsole();

            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            User user = new User(name, password, email, UserType.CUSTOMER);

            ConsoleService.spacingConsole();

            System.out.println("Details of the user:");
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Password: " + user.getPassword());
            System.out.println();

            do{
                System.out.print("Confirm your credentials? (y/n): ");
                choice = scanner.nextLine().toLowerCase();
                System.out.println();
                if (choice.equals("y") || choice.equals("n"))
                    breakLoop = true;
            } while (!breakLoop);

            breakLoop = false;

            switch (choice) {
                case "y" -> {
                    try {
                        int responseCode = UserData.register(user, Application.sessionToken);
                        ConsoleService.spacingConsole();
                        if (responseCode == 201) {
                            System.out.print("Press Enter to continue...");
                            scanner.nextLine();
                            breakLoop = true;
                        } else {
                            System.out.print("Press Enter to continue...");
                            scanner.nextLine();
                        }
                    } catch (URISyntaxException | IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "n" -> {
                    ConsoleService.spacingConsole();
                    System.out.println("Restarting Registration...");
                }
                default -> {
                    ConsoleService.spacingConsole();
                    System.out.println("Invalid choice. Please try again.");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                }
            }
        } while (!breakLoop);
    }
}
