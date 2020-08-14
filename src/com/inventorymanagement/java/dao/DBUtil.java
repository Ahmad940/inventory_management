/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/14/2020
 *  Time: 2:35 PM
 */
package com.inventorymanagement.java.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtil {
    private static Statement statement = null;
    private static DBUtil instance = new DBUtil();
    private Connection connection = DBConnection.getInstance().connection();

    public DBUtil() {
        try {
            statement = DBConnection.getInstance().connection().createStatement();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static DBUtil getInstance() {
        return instance;
    }

    public int counter(String query) {
        try {
            statement.executeQuery(query);
            ResultSet set = statement.getResultSet();
            set.absolute(1);
            return Integer.parseInt(set.getString(1));
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    public int statement(String query) {
        try {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }
}
