package com.shopping_cli.Menus;

import com.shopping_cli.Screens.ProductScreen;
import com.shopping_cli.data.ProductData;
import com.shopping_cli.entities.Product;
import com.shopping_cli.services.ConsoleService;
import com.shopping_cli.services.ListManagerService;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProductMenu {
    public static void start(Scanner scanner, List<Product> products, String categoryName) {
        ConsoleService.clearConsole();

        ListManagerService<Product> productListManager = new ListManagerService<>(products);

        boolean breakLoop;
        boolean isFind = false;
        String choice;

        do {
            breakLoop = false;

            List<Product> currentPageItems = productListManager.getCurrentPageItems();
            int currentPage = productListManager.getCurrentPage();
            int totalPages = productListManager.getTotalPages();

            System.out.println("==========================================");
            System.out.println("|             Shopping CLI               |");
            System.out.println("==========================================");
            dynamicMenuHeader(categoryName);
            System.out.println("==========================================");
            if (currentPage < totalPages)
                System.out.println("| N. Next Page                           |");
            if (currentPage != 1)
                System.out.println("| P. Previous Page                       |");
            System.out.println("| SN. Sort Products by name              |");
            System.out.println("| SP. Sort Products by price             |");
            if (isFind)
                System.out.println("| R. Return to all Products              |");
            System.out.println("| F. Search Product                      |");
            System.out.println("| 0. Back to Categories                  |");
            System.out.println("==========================================");

            displayProducts(currentPageItems, currentPage, totalPages);

            if (products.size() != 0) {
                System.out.print("Enter your choice: ");
                choice = scanner.nextLine().toLowerCase();
            } else {
                choice = "0";
            }

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
                    productListManager = new ListManagerService<>(ProductData.getAllProductsSortedByName());
                    ConsoleService.clearConsole();
                }
                case "sp" -> {
                    productListManager = new ListManagerService<>(ProductData.getAllProductsSortedByPrice());
                    ConsoleService.clearConsole();
                }
                case "r" -> {
                    isFind = false;
                }
                case "f" -> {
                    System.out.print("Search Term: ");
                    String searchTerm = scanner.nextLine();
                    System.out.println("Searching for " + searchTerm + "...");
                    productListManager = new ListManagerService<>(ProductData.getAllProductSearch(searchTerm));
                    isFind = true;
                    ConsoleService.clearConsole();
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


    private static void displayProducts(List<Product> products, int currentPage, int totalPages) {

        System.out.println();
        System.out.println("Page " + currentPage + " of " + totalPages);
        System.out.println();

        if (products.isEmpty()) {
            System.out.println("No products found.");
            ConsoleService.sleep(1000);
            return;
        }

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.println("- " + (i + 1) + ". " + product.getName() + " - " + product.getPrice() + "$");
        }

        System.out.println("==========================================");
    }

    private static void dynamicMenuHeader(String categoryName) {
        int staticLength = 40;
        int dynamicLength = categoryName.length();
        int padding = (staticLength - dynamicLength) / 2;

        StringBuilder sb = new StringBuilder();
        sb.append("|");
        sb.append(" ".repeat(Math.max(0, padding)));
        sb.append(categoryName);
        sb.append(" ".repeat(Math.max(0, padding)));
        sb.append("|");

        String paddedString = sb.toString();
        System.out.println(paddedString);
    }
}
