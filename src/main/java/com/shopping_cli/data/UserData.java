package com.shopping_cli.data;

import com.google.gson.Gson;
import com.shopping_cli.entities.User;

import java.io.IOException;
import java.net.HttpURLConnection;
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

    public static int register( User user) throws URISyntaxException, IOException, InterruptedException {
        String json = gson.toJson(user);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"users/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        int statusCode = 0;

        try {
            HttpResponse<Void> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.discarding());

            statusCode = response.statusCode();

            if (response.statusCode() == HttpURLConnection.HTTP_CREATED) {
                List<String> cookies = response.headers().map().get("Set-Cookie");
                if (cookies != null && !cookies.isEmpty()) {
                    SESSION_TOKEN = extractSessionToken(cookies.get(0));
                    System.out.println("User registered and logged in successfully.");
                }
            } else if (response.statusCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                System.out.println("Email already exists. Please try again.");
            } else {
                System.out.println("An error occurred during user registration. Please try again.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
            return statusCode;
    }

    public static int login( User user) {
        String json = gson.toJson(user);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"users/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        int statusCode = 0;

        try {
            HttpResponse<Void> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.discarding());

            statusCode = response.statusCode();

            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                List<String> cookies = response.headers().map().get("Set-Cookie");
                if (cookies != null && !cookies.isEmpty()) {
                    SESSION_TOKEN = extractSessionToken(cookies.get(0));
                    System.out.println("User logged in successfully.");
                }
            } else {
                System.out.println("Login failed. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return statusCode;
    }

    public static int logout(String sessionToken) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "users/logout"))
                .header("Cookie", sessionToken)
                .DELETE()
                .build();

        HttpResponse<Void> response = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.discarding());

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("User logged out successfully.");
        } else {
            System.out.println("An error occurred during logout. Please try again.");
        }
        return response.statusCode();
    }

    public static User getCurrentUser(Class<User> responseType) throws URISyntaxException, IOException, InterruptedException {
        System.out.println("sessionToken: " + SESSION_TOKEN);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"users/current"))
                .header("Cookie","JSESSIONID=" + SESSION_TOKEN)
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
