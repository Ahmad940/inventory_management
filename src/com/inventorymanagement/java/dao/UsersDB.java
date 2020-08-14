/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/13/2020
 *  Time: 1:07 PM
 */
package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.models.User;
import com.inventorymanagement.java.utils.DBConstants;
import com.inventorymanagement.java.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersDB {
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    private Connection connection = DBConnection.getInstance().connection();

    public UsersDB() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    // adding new user
    public int addUser(User user) {
        String query = "INSERT INTO " + DBConstants.TABLE_USERS + " VALUES(" +
                "?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getNumber());
            preparedStatement.setString(7, user.getPassword());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    // editing user
    public int editUser(int id, String firstName, String lastName, String gender, String number) {
        String query = "update " + DBConstants.TABLE_USERS + " set " + User.USER_FIRST_NAME + " =?, " +
                User.USER_LAST_NAME + " =?, " + User.USER_GENDER + "=?, " + User.USER_NUMBER + " =? " +
                " where id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, number);
            preparedStatement.setInt(5, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    //check if the user exist
    public int userExist(String email) {
        String query = "SELECT COUNT(" + User.USER_EMAIL + ") FROM " + DBConstants.TABLE_USERS +
                " WHERE " + User.USER_EMAIL + " = '" + email + "'";

        return DBUtil.getInstance().counter(query);
    }
}
