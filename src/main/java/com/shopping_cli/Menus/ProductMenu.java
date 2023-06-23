package com.shopping_cli.Menus;

import com.shopping_cli.entities.Category;
import com.shopping_cli.entities.Product;
import com.shopping_cli.services.ConsoleService;
import com.shopping_cli.services.ListManagerService;

import java.util.List;
import java.util.Scanner;

public class ProductMenu {
    public static void start(Scanner scanner, List<Product> products) {
        ConsoleService.clearConsole();

        ListManagerService<Product> productListManager = new ListManagerService<>(products);

        boolean breakLoop;

        String choice;

        do {
            breakLoop = false;

            List<Product> currentPageItems = productListManager.getCurrentPageItems();
            int currentPage = productListManager.getCurrentPage();
            int totalPages = productListManager.getTotalPages();

            System.out.println("==========================================");
            System.out.println("|             Shopping CLI               |");
            System.out.println("==========================================");
            System.out.println("|               Products                 |");
            System.out.println("==========================================");
            if(currentPage < totalPages )
                System.out.println("| N. Next Page                           |");
            if(currentPage != 1)
                System.out.println("| P. Previous Page                       |");
            System.out.println("| SN. Sort Products by name              |");
            System.out.println("| SP. Sort Products by price             |");
            System.out.println("| F. Search Product                      |");
            System.out.println("| 0. Back to Main Menu                   |");
            System.out.println("==========================================");

            displayCategories(currentPageItems, currentPage, totalPages);

            System.out.print("Enter your choice: ");
            choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "n" -> {
                    productListManager.goToNextPage();
                    ConsoleService.clearConsole();
                }
                case "p" -> {
                    productListManager.goToPreviousPage();
                    ConsoleService.clearConsole();
                }
                case "sn" -> {
                    System.out.println("Sort by name");
                    ConsoleService.clearConsole();
                }
                case "sp" -> {
                    System.out.println("Sort by price");
                    ConsoleService.clearConsole();
                }
                case "f" -> {
                    System.out.println("Find");
                    ConsoleService.clearConsole();
                }
                case "1" -> {
                    System.out.println("Product 1");
                    breakLoop = true;
                }
                case "2" -> {
                    System.out.println("Product 2");
                    breakLoop = true;
                }
                case "3" -> {
                    System.out.println("Product 3");
                    breakLoop = true;
                }
                case "4" -> {
                    System.out.println("Product 4");
                    breakLoop = true;
                }
                case "5" -> {
                    System.out.println("Product 5");
                    breakLoop = true;
                }
                case "0" -> {
                    System.out.println("Exiting...");
                    breakLoop = true;
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


    private static void displayCategories(List<Product> products, int currentPage, int totalPages) {

        System.out.println();
        System.out.println("Page " + currentPage + " of " + totalPages);
        System.out.println();

        int i = 1;

        for (Product category : products) {
            System.out.println("- " + i + ". " + category.getName());
            i++;
        }

        System.out.println("==========================================");
    }
}
