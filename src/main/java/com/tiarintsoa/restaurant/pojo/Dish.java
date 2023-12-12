package com.tiarintsoa.restaurant.pojo;

import java.math.BigDecimal;

public class Dish {
    private int id;
    private String name;
    private String image;
    private BigDecimal price;
    private BigDecimal product_price;
    private BigDecimal commission;

    // Constructors, getters, and setters

    public Dish() {
        // Default constructor
    }

    public Dish(int id, String name, String image, BigDecimal price, BigDecimal commission) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.commission = commission;
    }

    // Getter and Setter methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getProductPrice() {
        return product_price;
    }

    public void setProductPrice(BigDecimal product_price) {
        this.product_price = product_price;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    // toString method for debugging and logging

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", commission=" + commission +
                '}';
    }
}
