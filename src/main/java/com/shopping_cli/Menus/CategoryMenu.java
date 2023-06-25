package com.shopping_cli.Menus;

import com.shopping_cli.data.CategoryData;
import com.shopping_cli.data.ProductData;
import com.shopping_cli.entities.Category;
import com.shopping_cli.services.ConsoleService;
import com.shopping_cli.services.ListManagerService;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CategoryMenu {
    public static void start(Scanner scanner) {
        List<Category> categories = CategoryData.getAllCategories();
        ListManagerService<Category> categoryListManager = new ListManagerService<>(categories);
        boolean isFind = false;
        String choice;

        do {
            ConsoleService.clearConsole();
            int currentPage = categoryListManager.getCurrentPage();
            int totalPages = categoryListManager.getTotalPages();

            System.out.println("==========================================");
            System.out.println("|             Shopping CLI               |");
            System.out.println("==========================================");
            System.out.println("|              Categories                |");
            System.out.println("==========================================");
            System.out.println("| A. All Categories                      |");
            if (currentPage < totalPages)
                System.out.println("| N. Next Page                           |");
            if (currentPage != 1)
                System.out.println("| P. Previous Page                       |");
            if (isFind)
                System.out.println("| R. Return to all categories            |");
            System.out.println("| F. Find Category                       |");
            System.out.println("| 0. Back to Main Menu                   |");
            System.out.println("==========================================");

            displayCategories(categoryListManager.getCurrentPageItems(), currentPage, totalPages);

            System.out.print("Enter your choice: ");
            choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "n" -> {
                    categoryListManager.goToNextPage();
                }
                case "p" -> {
                    categoryListManager.goToPreviousPage();
                }
                case "r" -> {
                    categoryListManager = new ListManagerService<>(categories);
                    isFind = false;
                }
                case "f" -> {
                    System.out.print("Search Term: ");
                    String searchTerm = scanner.nextLine();
                    System.out.println("Searching for " + searchTerm + "...");
                    List<Category> filteredCategories = categories.stream()
                            .filter(category -> category.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                            .collect(Collectors.toList());
                    categoryListManager = new ListManagerService<>(filteredCategories);
                    isFind = true;
                }
                case "a" -> {
                    ProductMenu.start(scanner, ProductData.getAllProducts(), "All Categories");
                }
                case "0" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> {
                    ConsoleService.spacingConsole();
                    System.out.println("Invalid choice. Please try again.");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                }
            }
        } while (true);
    }

    private static void displayCategories(List<Category> categories, int currentPage, int totalPages) {
        System.out.println();
        System.out.println("Page " + currentPage + " of " + totalPages);
        System.out.println();

        int i = 1;
        for (Category category : categories) {
            System.out.println("- " + i + ". " + category.getName());
            i++;
        }

        System.out.println("==========================================");
    }
}
