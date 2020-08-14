/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/14/2020
 *  Time: 11:46 AM
 */
package com.inventorymanagement;

import com.inventorymanagement.java.dao.CategoriesDB;
import com.inventorymanagement.java.dao.ProductsDB;
import com.inventorymanagement.java.dao.RecordsDB;
import com.inventorymanagement.java.dao.UsersDB;
import com.inventorymanagement.java.models.Category;
import com.inventorymanagement.java.models.Product;
import com.inventorymanagement.java.models.Record;
import com.inventorymanagement.java.models.User;

import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        UsersDB usersDB = new UsersDB();
        CategoriesDB categoriesDB = new CategoriesDB();
        ProductsDB productsDB = new ProductsDB();
        RecordsDB recordsDB = new RecordsDB();
        User user = new User(0, "Ahmad", "Muhammad", "ahmadmuhammadmk5@gmail.com", "male", "09050273391", "ahmad");
        Category category = new Category(0, "bag", "");
        Product product = new Product(0, 10, 200.01, "jasengan", "It a punk", "Category");
        Record record = new Record(0, 200.2, "name", "bag", "it a description", LocalDateTime.now().toString());

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
