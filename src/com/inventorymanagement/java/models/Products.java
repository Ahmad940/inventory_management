/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/13/2020
 *  Time: 1:23 PM
 */
package com.inventorymanagement.java.models;

public class Products {
    public static final String PRODUCT_ID = "id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_DESCRIPTION = "product_description";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_NUMBER_IN_STOCK = "number_in_stock";
    private int id, price, noInStock;
    private String productName, productDescription;

    public Products() {
    }

    public Products(int id, int price, int noInStock, String productName, String productDescription) {
        this.id = id;
        this.price = price;
        this.noInStock = noInStock;
        this.productName = productName;
        this.productDescription = productDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNoInStock() {
        return noInStock;
    }

    public void setNoInStock(int noInStock) {
        this.noInStock = noInStock;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
