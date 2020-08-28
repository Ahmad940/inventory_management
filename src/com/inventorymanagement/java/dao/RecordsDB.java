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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    // get All records
    public List<Record> getAllRecord() {
        String query = "SELECT * FROM " + DBConstants.TABLE_RECORDS;
        List<Record> categoryList = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Record record = new Record();
                record.setId(resultSet.getInt(1));
                record.setProductName(resultSet.getString(2));
                record.setProductPrice(resultSet.getDouble(3));
                record.setDescription(resultSet.getString(4));
                record.setProductCategory(resultSet.getString(5));
                record.setAction(resultSet.getString(6));
                record.setDate(resultSet.getString(7));

                categoryList.add(record);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return categoryList;
    }

    // adding new record
    public int addRecord(Record record) {
        String query = "INSERT INTO " + DBConstants.TABLE_RECORDS + " VALUES(" +
                "?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, record.getProductName());
            preparedStatement.setDouble(3, record.getProductPrice());
            preparedStatement.setString(4, record.getDescription());
            preparedStatement.setString(5, record.getProductCategory());
            preparedStatement.setString(6, record.getAction());
            preparedStatement.setString(7, record.getDate());

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
