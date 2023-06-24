package com.shopping_cli.data;

import com.google.gson.Gson;
import com.shopping_cli.Application;
import com.shopping_cli.config.ApiConfig;
import com.shopping_cli.entities.User;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserData {
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static User getCurrentUser(Class<User> responseType, String sessionToken) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(ApiConfig.BASE_URL+"/users/current"))
                .header("cookie",sessionToken)
                .build();

        HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), responseType);
    }

    public static int register( User user, String sessionToken) throws URISyntaxException, IOException, InterruptedException {
        String json = gson.toJson(user);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(ApiConfig.BASE_URL+"users/register"))
                .header("Content-Type", "application/json")
                .header("Cookie", "sessionToken=" + sessionToken)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<Void> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.discarding());

        if (response.statusCode() == 201) {
            System.out.println("User created successfully.");
        } else if (response.statusCode() == 409) {
            System.out.println("Email already exists. Please try again.");
        } else {
            System.out.println("An error occurred during user registration. Please try again.");
        }
        return response.statusCode();
    }

    public static int login( User user, String sessionToken) throws URISyntaxException, IOException, InterruptedException {
        String json = gson.toJson(user);

        System.out.println(json);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(ApiConfig.BASE_URL+"users/login"))
                .header("Content-Type", "application/json")
                .header("Cookie", "sessionToken=" + sessionToken)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        System.out.println(postRequest);

        HttpResponse<Void> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.discarding());

        System.out.println(response.headers().firstValue("Set-Cookie").get().split(";")[0].split("=")[1]);

        if (response.statusCode() == 202) {
            Application.sessionToken = response.headers().firstValue("Set-Cookie").get().split(";")[0].split("=")[1];
            System.out.println("User logged in successfully.");
        } else if (response.statusCode() == 404) {
            System.out.println("Wrong email or password. Please try again.");
        } else {
            System.out.println("An error occurred during login. Please try again.");
        }
        return response.statusCode();
    }

    public static int logout(String sessionToken) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(new URI(ApiConfig.BASE_URL + "users/logout"))
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
}
