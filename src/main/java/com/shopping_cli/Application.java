package com.shopping_cli;

import com.shopping_cli.Menus.EntryMenu;
import com.shopping_cli.Screens.LoginScreen;
import com.shopping_cli.Screens.RegistrationScreen;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int returnValue;

        returnValue = EntryMenu.start(scanner);

        switch (returnValue) {
            case 1 -> RegistrationScreen.start(scanner);
            case 2 -> LoginScreen.start(scanner);
            default -> System.out.println("bye!");
        }

        scanner.close();
    }
}