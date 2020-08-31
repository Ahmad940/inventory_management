/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/28/2020
 *  Time: 9:44 AM
 */
package com.inventorymanagement.java.models;

public class Auth {
    private String email, password;

    public Auth() {
    }

    public Auth(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
