/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/14/2020
 *  Time: 11:03 AM
 */
package com.inventorymanagement.java.models;

public class Issue {
    public final static String PRODUCT_ID = "id";
    public final static String PRODUCT_Name = "product_name";
    public final static String PRODUCT_DESCRIPTION = "product_description";
    public final static String PRODUCT_PRICE = "product_price";
    public final static String PRODUCT_CATEGORY = "product_category";
    public final static String NUMBER_IN_STOCK = "numberInStock";
    public final static String DATE = "date";
    private int id, numberInStock;
    private double price;
    private String productName, productDescription, productCategory, date;

    public Issue() {
    }

    public Issue(
            int id, double price, String productName,
            String productDescription, String productCategory,
            int numberInStock, String date
    ) {
        this.id = id;
        this.price = price;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.numberInStock = numberInStock;
        this.date = date;
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

    public int getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(int numberInStock) {
        this.numberInStock = numberInStock;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
