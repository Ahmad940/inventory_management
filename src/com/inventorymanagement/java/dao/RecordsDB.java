/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/14/2020
 *  Time: 11:25 AM
 */
package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.models.Record;
import com.inventorymanagement.java.utils.DBConstants;
import com.inventorymanagement.java.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecordsDB {
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    private Connection connection = DBConnection.getInstance().connection();

    public RecordsDB() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    // adding new record
    public int addRecord(Record record) {
        String query = "INSERT INTO " + DBConstants.TABLE_RECORDS + " VALUES(" +
                "?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, record.getProductName());
            preparedStatement.setDouble(3, record.getProductPrice());
            preparedStatement.setString(4, record.getDescription());
            preparedStatement.setString(5, record.getProductCategory());
            preparedStatement.setString(6, record.getDate());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    // delete a record
    public int deleteRecord(int id) {
        String query = "DELETE FROM " + DBConstants.TABLE_RECORDS + "  WHERE " +
                Record.RECORD_ID + " = " + id + ";  ";

        return DBUtil.getInstance().statement(query);
    }
}
