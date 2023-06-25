package com.shopping_cli.Screens;

import com.shopping_cli.data.CartData;
import com.shopping_cli.data.CategoryData;
import com.shopping_cli.data.ProductData;
import com.shopping_cli.entities.Category;
import com.shopping_cli.entities.OrderItem;
import com.shopping_cli.entities.Product;
import com.shopping_cli.services.ConsoleService;
import com.shopping_cli.services.ListManagerService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

public class MyCartScreen {
    public static void start(Scanner scanner) {
        ConsoleService.clearConsole();

        boolean breakLoop;
        List<OrderItem> cartItems;
        List<Category> categories;
        List<Product> products;

        try {
            cartItems = CartData.getCart();
            categories = CategoryData.getAllCategories();
            products = ProductData.getAllProducts();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        ListManagerService<OrderItem> cartItemListManager = new ListManagerService<>(cartItems);
        List<OrderItem> currentPageItems;
        int currentPage;
        int totalPages;

        do {
            breakLoop = false;

            ConsoleService.clearConsole();

            System.out.println("==========================================");
            System.out.println("|             Shopping CLI               |");
            System.out.println("==========================================");
            System.out.println("|                My Cart                 |");
            System.out.println("==========================================");

            currentPageItems = cartItemListManager.getCurrentPageItems();
            currentPage = cartItemListManager.getCurrentPage();
            totalPages = cartItemListManager.getTotalPages();

            if (currentPage < totalPages) {
                System.out.println("| N. Next Page                           |");
            }
            if (currentPage != 1) {
                System.out.println("| P. Previous Page                       |");
            }
            System.out.println("|  1. Checkout                           |");
            System.out.println("|  2. Edit Cart                          |");
            System.out.println("|  0. Continue Shopping                  |");
            System.out.println("==========================================");

            displayCartItems(currentPageItems, categories, products, currentPage, totalPages);
            System.out.println();

            if (currentPage < totalPages) {
                System.out.print("N. Next Page");
            }

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "n" -> {
                    cartItemListManager.goToNextPage();
                    ConsoleService.clearConsole();
                }
                case "p" -> {
                    cartItemListManager.goToPreviousPage();
                    ConsoleService.clearConsole();
                }
                case "1" -> System.out.println("checkout"); // Checkout
                case "2" -> editCart(scanner, cartItems, categories, products); // Edit Cart
                case "0" -> breakLoop = true; // Continue Shopping
                default -> {
                    ConsoleService.spacingConsole();
                    System.out.println("Invalid choice. Please try again.");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    ConsoleService.spacingConsole();
                }
            }

            System.out.println();
        } while (!breakLoop);
    }

    private static void displayCartItems(List<OrderItem> cart,
                                         List<Category> categories,
                                         List<Product> products,
                                         int currentPage,
                                         int totalPages) {
        System.out.println();
        System.out.println("Page " + currentPage + " of " + totalPages);
        System.out.println();

        if (cart.isEmpty()) {
            System.out.println("No products found.");
            ConsoleService.sleep(1000);
            return;
        }

        int i = 1;
        for (OrderItem item : cart) {
            Product product = getProductById(products, item.getProduct().getId());
            Category category = getCategoryById(categories, product.getCategory().getId());

            String productName = product.getName();
            String categoryName = category.getName();

            System.out.println("- " + i
                    + ". " + productName
                    + " - " + categoryName
                    + " - " + "Amount: "
                    + item.getQuantity()
                    + item.getItemAmount() + "$");
            i++;
        }

        System.out.println("==========================================");
    }

    private static Product getProductById(List<Product> products, int productId) {
        return products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElse(null);
    }

    private static Category getCategoryById(List<Category> categories, int categoryId) {
        return categories.stream()
                .filter(category -> category.getId() == categoryId)
                .findFirst()
                .orElse(null);
    }

    private static void editCart(Scanner scanner,
                                 List<OrderItem> cart,
                                 List<Category> categories,
                                 List<Product> products) {
        boolean breakLoop;

        do {
            breakLoop = false;

            ConsoleService.clearConsole();

            System.out.println("==========================================");
            System.out.println("|             Shopping CLI               |");
            System.out.println("==========================================");
            System.out.println("|            Edit Cart Items             |");
            System.out.println("==========================================");
            System.out.println("|  1. Edit quantity                      |");
            System.out.println("|  2. Remove item                         |");
            System.out.println("|  0. Back to Cart                        |");
            System.out.println("==========================================");

            displayCartItems(cart, categories, products, -1, -1);

            System.out.println();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> editQuantity(scanner, cart);
                case "2" -> removeItem(scanner, cart);
                case "0" -> breakLoop = true;
                default -> {
                    ConsoleService.spacingConsole();
                    System.out.println("Invalid choice. Please try again.");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    ConsoleService.spacingConsole();
                }
            }
            System.out.println();
        } while (!breakLoop);

        System.out.println("Save changes in cart? Y/N");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("y")) {
            try {
                CartData.updateCart(cart);
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Changes saved.");
        }
    }

    private static void editQuantity(Scanner scanner, List<OrderItem> cart) {
        System.out.print("Enter the item number to edit quantity: ");
        int itemNumber = Integer.parseInt(scanner.nextLine());

        if (itemNumber >= 1 && itemNumber <= cart.size()) {
            OrderItem item = cart.get(itemNumber - 1);
            System.out.print("Enter the new quantity for item '" + item.getProduct().getName() + "': ");
            int newQuantity = Integer.parseInt(scanner.nextLine());

            item.setQuantity(newQuantity);
            item.setItemAmount(item.getProduct().getPrice() * newQuantity);

            System.out.println("Quantity updated successfully.");
            ConsoleService.sleep(1000);
        } else {
            System.out.println("Invalid item number. Please try again.");
            ConsoleService.sleep(1000);
        }
    }

    private static void removeItem(Scanner scanner, List<OrderItem> cart) {
        System.out.print("Enter the item number to remove: ");
        int itemNumber = Integer.parseInt(scanner.nextLine());

        if (itemNumber >= 1 && itemNumber <= cart.size()) {
            OrderItem item = cart.get(itemNumber - 1);
            cart.remove(item);

            System.out.println("Item removed successfully.");
            ConsoleService.sleep(1000);
        } else {
            System.out.println("Invalid item number. Please try again.");
            ConsoleService.sleep(1000);
        }
    }
}
