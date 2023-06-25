package com.shopping_cli.data;

import com.google.gson.Gson;
import com.shopping_cli.entities.OrderItem;
import com.shopping_cli.services.HttpClientService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import static com.shopping_cli.config.ApiConfig.BASE_URL;

public class CartData {
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    private static String SESSION_TOKEN;

    public static List<OrderItem> getCart() throws URISyntaxException, IOException, InterruptedException {
        String cartUrl = BASE_URL + "cart";

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(cartUrl))
                .header("Cookie", "JSESSIONID=" + SESSION_TOKEN)
                .build();

        HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        OrderItem[] cartItems = gson.fromJson(response.body(), OrderItem[].class);
        return Arrays.asList(cartItems);
    }

    public static void addToCart(OrderItem requestBody) throws URISyntaxException, IOException, InterruptedException {
        String json = gson.toJson(requestBody);
        String addToCartUrl = BASE_URL + "cart";

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(addToCartUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            List<String> cookies = response.headers().map().get("Set-Cookie");
            if (cookies != null && !cookies.isEmpty()) {
                SESSION_TOKEN = extractSessionToken(cookies.get(0));
                System.out.println("Item added to cart successfully.");
            }
        }
    }

    public static void updateCart(List<OrderItem> cart) throws URISyntaxException, IOException, InterruptedException {
        String updateCartUrl = BASE_URL + "cart";
        HttpClientService.postList(updateCartUrl, cart, OrderItem[].class);
    }

    public static void clearCart() throws URISyntaxException, IOException, InterruptedException {
        String clearCartUrl = BASE_URL + "cart";
        HttpClientService.delete(clearCartUrl);
    }

    public static void removeFromCart(OrderItem cartItem) throws URISyntaxException, IOException, InterruptedException {
        String removeFromCartUrl = BASE_URL + "cart/" + cartItem.getId();
        HttpClientService.delete(removeFromCartUrl);
    }

    private static String extractSessionToken(String cookie) {
        String[] parts = cookie.split(";");
        for (String part : parts) {
            if (part.trim().startsWith("JSESSIONID=")) {
                return part.split("=")[1];
            }
        }
        return null;
    }
}
