/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/14/2020
 *  Time: 11:46 AM
 */
package com.inventorymanagement;

import com.inventorymanagement.java.dao.CategoriesDB;
import com.inventorymanagement.java.dao.UsersDB;
import com.inventorymanagement.java.models.Category;
import com.inventorymanagement.java.models.User;

public class Test {
    public static void main(String[] args) {
        UsersDB usersDB = new UsersDB();
        CategoriesDB categoriesDB = new CategoriesDB();
        User user = new User(0, "Ahmad", "Muhammad", "ahmadmuhammadmk5@gmail.com", "male", "09050273391", "ahmad");
        Category category = new Category(0, "bag", "");

//        System.out.println("added: " + usersDB.addUser(user));
//        System.out.println(usersDB.editUser(1, "Ahmad", "Muhammad", "male", "0902425002"));
//        System.out.println(usersDB.userExist("ahmadmuhammadmk5@gmail.com"));
//        System.out.println(categoriesDB.editCategory(3, "bag", ""));
//        System.out.println(categoriesDB.deleteCategory(5));
    }
}
