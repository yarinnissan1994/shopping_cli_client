package com.shopping_cli.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListManagerService<T> {
    private final List<T> items;
    private final int itemsPerPage;

    public ListManagerService() {
        items = new ArrayList<>();
        itemsPerPage = 5;
    }

    public void addItem(T item) {
        items.add(item);
    }

    public void removeItem(T item) {
        items.remove(item);
    }

    public List<T> getItems() {
        return items;
    }

    public List<T> getItemsByPage(int pageNumber) {
        int startIndex = (pageNumber - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, items.size());
        return items.subList(startIndex, endIndex);
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) items.size() / itemsPerPage);
    }

    public void sortBy(Comparator<T> comparator) {
        items.sort(comparator);
    }

    public List<T> searchByName(String keyword) {
        String lowerCasedKeyword = keyword.toLowerCase();
        return items.stream()
                .filter(item -> item.toString().toLowerCase().contains(lowerCasedKeyword))
                .collect(Collectors.toList());
    }

    public void clear() {
        items.clear();
    }
}
