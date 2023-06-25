package com.shopping_cli.data;

import com.google.gson.Gson;
import com.shopping_cli.entities.OrderItem;
import com.shopping_cli.entities.User;
import com.shopping_cli.services.HttpClientService;

import java.io.IOException;
import java.net.HttpURLConnection;
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
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"cart"))
                .header("Cookie","JSESSIONID=" + SESSION_TOKEN)
                .build();

        HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        OrderItem[] cartItems = gson.fromJson(response.body(), OrderItem[].class);
        return Arrays.asList(cartItems);
    }

    public static void addToCart(OrderItem requestBody) throws URISyntaxException, IOException, InterruptedException {
        String json = gson.toJson(requestBody);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"cart"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        response.statusCode();

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            List<String> cookies = response.headers().map().get("Set-Cookie");
            if (cookies != null && !cookies.isEmpty()) {
                SESSION_TOKEN = extractSessionToken(cookies.get(0));
                System.out.println("Item added to cart successfully.");
            }
        }
    }

//    public static void addToCart2(OrderItem cartItem) throws URISyntaxException, IOException, InterruptedException {
//        HttpClientService.post(BASE_URL+"cart",  cartItem, OrderItem.class);
//    }

    public static void updateCart(List<OrderItem> Cart) throws URISyntaxException, IOException, InterruptedException {
        HttpClientService.postList(BASE_URL+"cart", Cart, OrderItem[].class);
    }

    public static void clearCart() throws URISyntaxException, IOException, InterruptedException {
        HttpClientService.delete(BASE_URL+"cart");
    }

    public static void removeFromCart(OrderItem cartItem) throws URISyntaxException, IOException, InterruptedException {
        HttpClientService.delete(BASE_URL+"cart/"+cartItem.getId());
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
