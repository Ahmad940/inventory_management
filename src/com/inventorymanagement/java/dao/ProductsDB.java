/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/13/2020
 *  Time: 1:07 PM
 */
package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.models.Product;
import com.inventorymanagement.java.utils.DBConstants;
import com.inventorymanagement.java.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsDB {
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    private Connection connection = DBConnection.getInstance().connection();

    public ProductsDB() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    // get All products
    public List<Product> getAllProducts() {
        String query = "SELECT * FROM " + DBConstants.TABLE_PRODUCTS;
        List<Product> productList = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setProductName(resultSet.getString(2));
                product.setProductDescription(resultSet.getString(3));
                product.setPrice(resultSet.getDouble(4));
                product.setNoInStock(resultSet.getInt(5));
                product.setProductCategory(resultSet.getString(6));

                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return productList;
    }

    // adding new product
    public int addProduct(Product product) {
        String query = "INSERT INTO " + DBConstants.TABLE_PRODUCTS + " VALUES(" +
                "?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setString(3, product.getProductDescription());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getNoInStock());
            preparedStatement.setString(6, product.getProductCategory());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    // editing products
    public int editProducts(int id, String productName, String productDescription, double price, int noInStock, String category) {
        String query = "update " + DBConstants.TABLE_PRODUCTS + " set " + Product.PRODUCT_NAME + " =?, " +
                Product.PRODUCT_DESCRIPTION + " =?, " + Product.PRODUCT_PRICE + "=?, " + Product.PRODUCT_CATEGORY + " =?, " +
                Product.PRODUCT_NUMBER_IN_STOCK + " =? where id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, productDescription);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, category);
            preparedStatement.setInt(5, noInStock);
            preparedStatement.setInt(6, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    //check if the product exist
    public int productExist(String productName) {
        String query = "SELECT COUNT(" + Product.PRODUCT_NAME + ") FROM " + DBConstants.TABLE_PRODUCTS +
                " WHERE " + Product.PRODUCT_NAME + " = '" + productName + "'";

        return DBUtil.getInstance().counter(query);
    }

    // delete a product
    public int deleteProduct(int id) {
        String query = "DELETE FROM " + DBConstants.TABLE_PRODUCTS + "  WHERE " +
                Product.PRODUCT_ID + " = " + id + ";  ";

        return DBUtil.getInstance().statement(query);
    }

    // purchase
    public int issuePurchase(int id, int newQuantity) {
        String query = "update " + DBConstants.TABLE_PRODUCTS + " set " +
                Product.PRODUCT_NUMBER_IN_STOCK + " =? where id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }
}
