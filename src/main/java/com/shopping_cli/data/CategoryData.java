package com.shopping_cli.data;

import com.shopping_cli.config.ApiConfig;
import com.shopping_cli.entities.Category;
import com.shopping_cli.services.HttpClientService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class CategoryData {
    private static String baseUrl = ApiConfig.BASE_URL+"categories";

    public static List<Category> getAllCategories() {
        try {
            return HttpClientService.getList(baseUrl, Category[].class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
