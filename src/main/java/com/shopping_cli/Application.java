package com.shopping_cli;

import com.shopping_cli.Menus.CategoryMenu;
import com.shopping_cli.entities.Category;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        int returnValue;
//
//        returnValue = EntryMenu.start(scanner);
//
//        switch (returnValue) {
//            case 1 -> RegistrationScreen.start(scanner);
//            case 2 -> LoginScreen.start(scanner);
//            default -> System.out.println("bye!");
//        }

        List<Category> categories = new ArrayList<>();
        Collections.addAll(categories,
                new Category(1, "Electronics"),
                new Category(2, "Food"),
                new Category(3, "Clothes"),
                new Category(4, "Toys"),
                new Category(5, "Books"),
                new Category(6, "Sports"),
                new Category(7, "Others")
        );

        CategoryMenu.start(scanner, categories);

        scanner.close();
    }
}