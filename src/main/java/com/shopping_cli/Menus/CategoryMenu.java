package com.shopping_cli.Menus;

import com.shopping_cli.services.ConsoleService;
import com.shopping_cli.services.ListManagerService;

import java.util.List;
import java.util.Scanner;

public class CategoryMenu {
    public static void start(Scanner scanner, List<com.shopping_cli.entities.Category> categories) {
        ConsoleService.clearConsole();

        ListManagerService<com.shopping_cli.entities.Category> categoryListManager = new ListManagerService<>(categories);

        boolean breakLoop;

        String choice;

        do {
            breakLoop = false;

            List<com.shopping_cli.entities.Category> currentPageItems = categoryListManager.getCurrentPageItems();
            int currentPage = categoryListManager.getCurrentPage();
            int totalPages = categoryListManager.getTotalPages();

            System.out.println("==========================================");
            System.out.println("|             Shopping CLI               |");
            System.out.println("==========================================");
            System.out.println("|             Category Menu              |");
            System.out.println("==========================================");
            if(currentPage < totalPages )
                System.out.println("| N. Next Page                           |");
            if(currentPage != 1)
                System.out.println("| P. Previous Page                       |");
            System.out.println("| F. Search Category                     |");
            System.out.println("| 0. Back to Main Menu                   |");
            System.out.println("==========================================");

            displayCategories(currentPageItems, currentPage, totalPages);

            System.out.print("Enter your choice: ");
            choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "n" -> {
                    categoryListManager.goToNextPage();
                    ConsoleService.clearConsole();
                }
                case "p" -> {
                    categoryListManager.goToPreviousPage();
                    ConsoleService.clearConsole();
                }
                case "f" -> {
                    System.out.println("Find");
                    ConsoleService.clearConsole();
                }
                case "1" -> {
                    System.out.println("category 1");
                    breakLoop = true;
                }
                case "2" -> {
                    System.out.println("category 2");
                    breakLoop = true;
                }
                case "3" -> {
                    System.out.println("category 3");
                    breakLoop = true;
                }
                case "4" -> {
                    System.out.println("category 4");
                    breakLoop = true;
                }
                case "5" -> {
                    System.out.println("category 5");
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


    private static void displayCategories(List<com.shopping_cli.entities.Category> categories, int currentPage, int totalPages) {

        System.out.println();
        System.out.println("Page " + currentPage + " of " + totalPages);
        System.out.println();

        int i = 1;

        for (com.shopping_cli.entities.Category category : categories) {
            System.out.println("- " + i + ". " + category.getName());
            i++;
        }

        System.out.println("==========================================");
    }
}
