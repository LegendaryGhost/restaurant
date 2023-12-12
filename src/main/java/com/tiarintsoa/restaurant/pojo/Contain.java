package com.tiarintsoa.restaurant.pojo;

import java.math.BigDecimal;

public class Contain {

    private int id_dish;
    private int id_command;
    private int quantity;
    private BigDecimal price;
    private BigDecimal product_price;
    private BigDecimal commission;

    public Contain(int id_dish, int id_command, int quantity, BigDecimal price, BigDecimal product_price, BigDecimal commission) {
        this.id_dish = id_dish;
        this.id_command = id_command;
        this.quantity = quantity;
        this.price = price;
        this.product_price = product_price;
        this.commission = commission;
    }

    public Contain() {
    }

    public int getId_dish() {
        return id_dish;
    }

    public void setId_dish(int id_dish) {
        this.id_dish = id_dish;
    }

    public int getId_command() {
        return id_command;
    }

    public void setId_command(int id_command) {
        this.id_command = id_command;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getProduct_price() {
        return product_price;
    }

    public void setProduct_price(BigDecimal product_price) {
        this.product_price = product_price;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }
}
