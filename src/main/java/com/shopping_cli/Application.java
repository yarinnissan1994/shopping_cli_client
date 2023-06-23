package com.shopping_cli;

import com.shopping_cli.Menus.EntryMenu;
import com.shopping_cli.Screens.LoginScreen;
import com.shopping_cli.Screens.RegisterationScreen;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int returnValue;

        returnValue = EntryMenu.start(scanner);
        if(returnValue == 1)
            RegisterationScreen.start(scanner);
        else if(returnValue == 2)
            LoginScreen.start(scanner);
        else
            System.out.println("bye!");

        scanner.close();
    }
}