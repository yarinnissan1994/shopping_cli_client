package com.shopping_cli.Screens;

import com.shopping_cli.Application;
import com.shopping_cli.entities.User;
import com.shopping_cli.services.ConsoleService;
import com.shopping_cli.data.UserData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class LoginScreen {
    public static void start(Scanner scanner) {

        boolean breakLoop = false;
        String choice;

        do {
            ConsoleService.clearConsole();

            System.out.println("==========================================");
            System.out.println("|              Shopping CLI              |");
            System.out.println("==========================================");
            System.out.println("|               Login Page               |");
            System.out.println("==========================================");
            System.out.println();
            System.out.print("Press Enter to continue login or type 0 to exit: ");

            choice = scanner.nextLine();

            if(choice.equals("0")) return;

            ConsoleService.spacingConsole();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            int responseCode = UserData.login(user);
            ConsoleService.spacingConsole();
            if (responseCode == 200) {
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                breakLoop = true;
            } else {
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            }

            System.out.println();
        } while (!breakLoop);
    }
}
