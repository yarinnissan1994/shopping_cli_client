package com.shopping_cli;

import com.shopping_cli.Menus.CategoryMenu;
import com.shopping_cli.Menus.EntryMenu;
import com.shopping_cli.Menus.MainMenu;
import com.shopping_cli.Screens.*;
import com.shopping_cli.data.UserData;
import com.shopping_cli.entities.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Application {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int returnValue;

        returnValue = EntryMenu.start(scanner);

        switch (returnValue) {
            case 1 -> RegistrationScreen.start(scanner);
            case 2 -> LoginScreen.start(scanner);
        }

        while (returnValue != 0){
            returnValue = MainMenu.start(scanner);
            switch(returnValue) {
                case 1 -> CategoryMenu.start(scanner);
                case 2 -> MyCartScreen.start(scanner);
                case 3 -> CheckoutScreen.start(scanner);
                case 4 -> ProfileScreen.start(scanner);
            };
        }

        try {
            User user = UserData.getCurrentUser(User.class);
            System.out.println(user);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        scanner.close();
    }
}