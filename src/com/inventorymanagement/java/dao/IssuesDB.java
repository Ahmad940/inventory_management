/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/14/2020
 *  Time: 11:25 AM
 */
package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.models.Issue;
import com.inventorymanagement.java.utils.DBConstants;
import com.inventorymanagement.java.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IssuesDB {
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    private Connection connection = DBConnection.getInstance().connection();

    public IssuesDB() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    // get All issues
    public List<Issue> getAllIssues() {
        String query = "SELECT * FROM " + DBConstants.TABLE_ISSUES;
        List<Issue> issueList = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Issue issue = new Issue();
                issue.setId(resultSet.getInt(1));
                issue.setProductName(resultSet.getString(2));
                issue.setPrice(resultSet.getDouble(3));
                issue.setProductDescription(resultSet.getString(4));
                issue.setProductCategory(resultSet.getString(5));
                issue.setNumberInStock(resultSet.getInt(6));
                issue.setDate(resultSet.getString(7));

                issueList.add(issue);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return issueList;
    }

    // get All issue
    public int addIssue(Issue issue) {
        String query = "INSERT INTO " + DBConstants.TABLE_ISSUES + " VALUES(" +
                "?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, issue.getProductName());
            preparedStatement.setDouble(3, issue.getPrice());
            preparedStatement.setString(4, issue.getProductDescription());
            preparedStatement.setString(5, issue.getProductCategory());
            preparedStatement.setInt(6, issue.getNumberInStock());
            preparedStatement.setString(7, issue.getDate());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    // delete issue
    public int deleteIssue(int id) {
        String query = "DELETE FROM " + DBConstants.TABLE_ISSUES + "  WHERE " +
                Issue.PRODUCT_ID + " = " + id + ";  ";

        return DBUtil.getInstance().statement(query);
    }
}
