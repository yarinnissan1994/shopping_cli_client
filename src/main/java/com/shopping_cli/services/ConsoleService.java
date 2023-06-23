package com.shopping_cli.services;

public class ConsoleService {
    public static void clearConsole() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    public static void spacingConsole() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }
}
