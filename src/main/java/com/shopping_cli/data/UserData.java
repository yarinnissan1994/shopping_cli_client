package com.shopping_cli.data;

import com.google.gson.Gson;
import com.shopping_cli.entities.User;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static com.shopping_cli.config.ApiConfig.BASE_URL;


public class UserData {
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    private static String SESSION_TOKEN;

    public static int register(User user) throws URISyntaxException, IOException, InterruptedException {
        String json = gson.toJson(user);
        String registerUrl = BASE_URL + "users/register";
        return sendPostRequest(registerUrl, json, true);
    }

    public static int login(User user) throws URISyntaxException, IOException, InterruptedException {
        String json = gson.toJson(user);
        String loginUrl = BASE_URL + "users/login";
        return sendPostRequest(loginUrl, json, false);
    }

    private static int sendPostRequest(String url, String json, boolean isRegister) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<Void> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.discarding());

        int statusCode = response.statusCode();

        if (statusCode == 201 || statusCode == 200) {
            List<String> cookies = response.headers().map().get("Set-Cookie");
            if (cookies != null && !cookies.isEmpty()) {
                SESSION_TOKEN = extractSessionToken(cookies.get(0));
                if (isRegister) {
                    System.out.println("User registered successfully.");
                } else {
                    System.out.println("User logged in successfully.");
                }
            }
        } else if (statusCode == 401) {
            if (isRegister) {
                System.out.println("Email already exists. Please try again.");
            } else {
                System.out.println("Incorrect email or password. Please try again.");
            }
        } else {
            System.out.println("An error occurred. Please try again.");
        }

        return statusCode;
    }

    public static int logout(String sessionToken) throws URISyntaxException, IOException, InterruptedException {
        String logoutUrl = BASE_URL + "users/logout";

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(new URI(logoutUrl))
                .header("Cookie", sessionToken)
                .DELETE()
                .build();

        HttpResponse<Void> response = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.discarding());

        if (response.statusCode() == 200) {
            System.out.println("User logged out successfully.");
        } else {
            System.out.println("An error occurred during logout. Please try again.");
        }

        return response.statusCode();
    }

    public static User getCurrentUser(Class<User> responseType) throws URISyntaxException, IOException, InterruptedException {
        System.out.println("sessionToken: " + SESSION_TOKEN);

        String currentUserUrl = BASE_URL + "users/current";

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(currentUserUrl))
                .header("Cookie", "JSESSIONID=" + SESSION_TOKEN)
                .build();

        HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), responseType);
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
