/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/13/2020
 *  Time: 1:07 PM
 */
package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.models.Category;
import com.inventorymanagement.java.utils.DBConstants;
import com.inventorymanagement.java.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriesDB {
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    private Connection connection = DBConnection.getInstance().connection();

    public CategoriesDB() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    // add category;
    public int addCategory(Category category) {
        String query = "INSERT INTO " + DBConstants.TABLE_CATEGORIES + " VALUES(" +
                "?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, category.getCategoryName());
            preparedStatement.setString(3, category.getCategoryDescription());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    //edit category
    public int editCategory(int id, String categoryName, String categoryDescription) {
        String query = "update " + DBConstants.TABLE_CATEGORIES + " set " + Category.CATEGORY_NAME + " =?, " +
                Category.CATEGORY_DESCRIPTION + " =? " +
                " where id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, categoryName);
            preparedStatement.setString(2, categoryDescription);
            preparedStatement.setInt(3, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    // delete a category
    public int deleteCategory(int id) {
        String query = "DELETE FROM " + DBConstants.TABLE_CATEGORIES + "  WHERE " +
                Category.CATEGORY_ID + " = " + id + ";  ";

        return DBUtil.getInstance().statement(query);
    }

    //check if the category exist
    public int categoryExist(String categoryName) {
        String query = "SELECT COUNT(" + Category.CATEGORY_NAME + ") FROM " + DBConstants.TABLE_CATEGORIES +
                " WHERE " + Category.CATEGORY_NAME + " = '" + categoryName + "'";

        return DBUtil.getInstance().counter(query);
    }
}
