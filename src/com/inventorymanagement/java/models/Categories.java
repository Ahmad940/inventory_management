/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/13/2020
 *  Time: 1:23 PM
 */
package com.inventorymanagement.java.models;

public class Categories {
    public static final String CATEGORY_ID = "id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_DESCRIPTION = "category_description";
    private int id;
    private String categoryName, categoryDescription;

    public Categories() {
    }

    public Categories(int id, String categoryName, String categoryDescription) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
