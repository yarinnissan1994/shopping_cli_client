package com.shopping_cli.entities;

public class OrderItem {
    private int id;
    private Order order;
    private Product product;
    private int quantity;
    private double itemAmount;

    public OrderItem(Product product, int quantity, double itemAmount) {
        this.product = product;
        this.quantity = quantity;
        this.itemAmount = itemAmount;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(double itemAmount) {
        this.itemAmount = itemAmount;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                ", itemAmount=" + itemAmount +
                '}';
    }
}
