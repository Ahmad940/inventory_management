/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/13/2020
 *  Time: 1:23 PM
 */
package com.inventorymanagement.java.models;

public class Product {
    public static final String PRODUCT_CATEGORY = "product_category";
    private int id, noInStock;
    private Double price;


    public static final String PRODUCT_ID = "id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_DESCRIPTION = "product_description";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_NUMBER_IN_STOCK = "number_in_stock";
    private String productName, productDescription, productCategory;

    public Product() {
    }

    public Product(int id, int noInStock, Double price, String productName, String productDescription, String productCategory) {
        this.id = id;
        this.noInStock = noInStock;
        this.price = price;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoInStock() {
        return noInStock;
    }

    public void setNoInStock(int noInStock) {
        this.noInStock = noInStock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}
