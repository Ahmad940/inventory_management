/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/14/2020
 *  Time: 11:03 AM
 */
package com.inventorymanagement.java.models;

public class Issues {
    public final static String PRODUCT_ID = "id";
    public final static String PRODUCT_Name = "product_name";
    public final static String PRODUCT_DESCRIPTION = "product_description";
    public final static String PRODUCT_PRICE = "product_price";
    public final static String PRODUCT_CATEGORY = "product_category";
    public final static String DATE = "date";
    private int id;
    private double price;
    private String productName, productDescription, productCategory;

    public Issues() {
    }

    public Issues(int id, double price, String productName, String productDescription, String productCategory) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
