package com.shopping_cli.data;

import com.shopping_cli.config.ApiConfig;
import com.shopping_cli.entities.Category;
import com.shopping_cli.entities.Product;
import com.shopping_cli.services.HttpClientService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ProductData {
    private static String baseUrl = ApiConfig.BASE_URL+"products";

    public static List<Product> getAllProducts() {
        try {
            return HttpClientService.getList(baseUrl, Product[].class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Product> getAllProductsByCategory(int categoryId) {
        try {
            return HttpClientService.getList(baseUrl+"/category/"+categoryId, Product[].class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
