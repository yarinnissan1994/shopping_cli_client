package com.shopping_cli.services;

import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class HttpClientService {
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static <T> T get(String url, Class<T> responseType) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), responseType);
    }

    public static <T> List<T> getList(String url, Class<T[]> responseType) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        T[] array = gson.fromJson(response.body(), responseType);
        return Arrays.asList(array);
    }

    public static <T> void post(String url, com.shopping_cli.entities.OrderItem requestBody, Class<T> responseType) throws URISyntaxException, IOException, InterruptedException {
        String json = gson.toJson(requestBody);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        response.statusCode();
    }

    public static <T> void postList(String url, List<T> list, Class<T[]> responseType) throws URISyntaxException, IOException, InterruptedException {
        T[] array = list.toArray((T[]) Array.newInstance(responseType.getComponentType(), list.size()));
        String json = gson.toJson(array);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        response.statusCode();
    }


    public static <T> void put(String url, com.shopping_cli.entities.OrderItem requestBody, Class<T> responseType) throws URISyntaxException, IOException, InterruptedException {
        String json = gson.toJson(requestBody);

        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());
        response.statusCode();
        response.body();
    }

    public static void delete(String url) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();

        httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
    }
}

//    String baseUrl = "http://localhost:8080/api/users";
//
//        User user = new User("HaShachen", "test", "XXXXXXXXXXXXX", UserType.CUSTOMER);
//
//        try {
//            // Perform GET request
//            User fetchedUser = HttpClientService.get(baseUrl + "/5", User.class);
//            System.out.println(fetchedUser);
//
//            // Perform POST request
//            String createdUser = HttpClientService.post(baseUrl, user, User.class);
//            System.out.println(createdUser);
//
//            // Perform PUT request
//            fetchedUser.setName("Amir");
//            String updatedUser = HttpClientService.put(baseUrl + "/4", fetchedUser, User.class);
//            System.out.println(updatedUser);
//
//            // Perform DELETE request
//            HttpClientService.delete(baseUrl + "/" + fetchedUser.getId());
//        } catch (IOException | InterruptedException | URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
