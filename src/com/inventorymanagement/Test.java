/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/14/2020
 *  Time: 11:46 AM
 */
package com.inventorymanagement;

import com.inventorymanagement.java.dao.*;
import com.inventorymanagement.java.models.*;

import java.time.LocalDateTime;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        UsersDB usersDB = new UsersDB();
        CategoriesDB categoriesDB = new CategoriesDB();
        ProductsDB productsDB = new ProductsDB();
        RecordsDB recordsDB = new RecordsDB();
        IssuesDB issuesDB = new IssuesDB();
        User user = new User(0, "Ahmad", "Muhammad", "ahmadmuhammadmk5@gmail.com", "male", "09050273391", "ahmad");
        Category category = new Category(0, "bag", "");
        Product product = new Product(0, 10, 200.01, "jasengan", "It a punk", "Category");
        Record record = new Record(0, 200.2, "name", "bag", "it a description", LocalDateTime.now().toString());

        List<Product> productList = productsDB.getAllProducts();
        List<Category> categoryList = categoriesDB.getAllCategories();
        List<Record> recordsList = recordsDB.getAllRecord();
        List<Issue> issuesList = issuesDB.getAllIssues();
//        productList.forEach(product1 -> {
//            System.out.println("Product id " + product1.getId() + " product Nane " + product1.getProductName());
//        });
//        categoryList.forEach(category1 -> {
//            System.out.println("CId: " + category1.getId() + " CName: " + category1.getCategoryName() +
//                    " cDescription: " + category1.getCategoryDescription());
//        });
//        recordsList.forEach(record1 -> {
//            System.out.println("CId: " + record1.getId() + " CName: " + record1.getProductCategory() +
//                    " cDescription: " + record1.getDescription());
//        });
        issuesList.forEach(issue -> {
            System.out.println("CId: " + issue.getId() + " CName: " + issue.getProductCategory() +
                    " cDescription: " + issue.getProductDescription());
        });

//        System.out.println("added: " + usersDB.addUser(user));
//        System.out.println(usersDB.editUser(1, "Ahmad", "Muhammad", "male", "0902425002"));
//        System.out.println(usersDB.userExist("ahmadmuhammadmk5@gmail.com"));
//        System.out.println(categoriesDB.editCategory(3, "bag", ""));
//        System.out.println(categoriesDB.deleteCategory(5));
//        System.out.println(productsDB.addProduct(product));
//        System.out.println(productsDB.editProducts(4, "Hello", "It a new product bla bla", 40.5, 23, "bag"));
//        System.out.println(productsDB.productExist("Hello"));
//        System.out.println(productsDB.deleteProduct(2));
//        System.out.println(recordsDB.addRecord(record));
//        System.out.println(recordsDB.deleteRecord(1));
    }
}
