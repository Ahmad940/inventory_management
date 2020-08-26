/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/14/2020
 *  Time: 11:25 AM
 */
package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.models.Issue;
import com.inventorymanagement.java.utils.DBConstants;

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
                issue.setDate(resultSet.getString(6));

                issueList.add(issue);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return issueList;
    }
}
