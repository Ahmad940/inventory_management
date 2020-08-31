/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/13/2020
 *  Time: 11:26 AM
 */
package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.models.*;
import com.inventorymanagement.java.utils.Alerts;
import com.inventorymanagement.java.utils.DBConstants;
import com.inventorymanagement.java.utils.configs.db.DBDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    /*
     * getting the connection data from the dbconfigclass
     * @Reference DBDataSource()
     * */
    private final String CONNECTION_STRING = DBDataSource.getInstance().getDatasource();
    private final String USER = DBDataSource.getInstance().getUser();
    private final String PASSWORD = DBDataSource.getInstance().getPassword();

    private static DBConnection instance = new DBConnection();

    public static DBConnection getInstance() {
        return instance;
    }

    public DBConnection() {
        createTables();
    }

    public static void main(String[] args) {
        DBConnection connection = new DBConnection();
    }

    // the connection method which made the connection
    public Connection connection() {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
            Logger.getLogger(getClass().getName()).log(Level.INFO, "Database Connection success");
            return connection;
        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            Alerts.jfxAlertShowAndWait("Error", "Unable to connect to database");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    // automating table creation
    private void createTables() {
        try {
            Statement statement = connection().createStatement();

            // the users table
            String usersTable = "CREATE TABLE IF NOT EXISTS  `inventorymanagement`.`" + DBConstants.TABLE_USERS +
                    "` ( `" + User.USER_ID + "` INT NOT NULL AUTO_INCREMENT , `" + User.USER_FIRST_NAME +
                    "` VARCHAR(32) NOT NULL , `" + User.USER_LAST_NAME + "` VARCHAR(32) NOT NULL , `" +
                    User.USER_EMAIL + "` VARCHAR(64) NOT NULL UNIQUE , `" + User.USER_GENDER + "` VARCHAR(10) NOT NULL ," +
                    " `" + User.USER_NUMBER + "` VARCHAR(15) NOT NULL , `" + User.USER_PASSWORD + "` VARCHAR(1024) NOT NULL," +
                    " PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            // the categories table
            String categoriesTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + DBConstants.TABLE_CATEGORIES +
                    "` ( `" + Category.CATEGORY_ID + "` INT NOT NULL AUTO_INCREMENT , `" + Category.CATEGORY_NAME +
                    "` VARCHAR(32) NOT NULL UNIQUE ,  `" + Category.CATEGORY_DESCRIPTION + "` VARCHAR(64) ," +
                    " PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            // the products table
            String productsTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + DBConstants.TABLE_PRODUCTS +
                    "` ( `" + Product.PRODUCT_ID + "` INT NOT NULL AUTO_INCREMENT , `" +
                    Product.PRODUCT_NAME + "` VARCHAR(32) NOT NULL , `" +
                    Product.PRODUCT_DESCRIPTION + "` VARCHAR(64) NOT NULL , `" + Product.PRODUCT_PRICE + "` DOUBLE NOT NULL , `" +
                    Product.PRODUCT_NUMBER_IN_STOCK + "` INT NOT NULL , `" + Product.PRODUCT_CATEGORY +
                    "` VARCHAR(32) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            String recordTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + DBConstants.TABLE_RECORDS +
                    "` ( `" + Record.RECORD_ID + "` INT NOT NULL AUTO_INCREMENT, `" +
                    Record.RECORD_PRODUCT_NAME + "` VARCHAR(32) NOT NULL, `" + Record.RECORD_PRICE +
                    "` DOUBLE NOT NULL, ` " + Record.RECORD_DESCRIPTION + "` VARCHAR(64) NOT NULL, `" +
                    Record.RECORD_CATEGORY + "` VARCHAR(30) NOT NULL, `" + Record.RECORD_ACTION + "` " +
                    "VARCHAR(32) NOT NULL, `" + Record.RECORD_DATE + "`VARCHAR(32) NOT NULL," +
                    " PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            String issuesTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + DBConstants.TABLE_ISSUES +
                    "` ( `" + Issue.PRODUCT_ID + "` INT NOT NULL AUTO_INCREMENT, `" +
                    Issue.PRODUCT_Name + "` VARCHAR(32) NOT NULL, `" + Issue.PRODUCT_PRICE +
                    "` DOUBLE NOT NULL, ` " + Issue.PRODUCT_DESCRIPTION + "` VARCHAR(64) NOT NULL, `" +
                    Issue.PRODUCT_CATEGORY + "` VARCHAR(32) NOT NULL, `" + Issue.NUMBER_IN_STOCK + "` INT NOT NULL , `" +
                    Issue.DATE + "` VARCHAR(32) NOT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            statement.executeUpdate(usersTable);
            statement.executeUpdate(productsTable);
            statement.executeUpdate(categoriesTable);
            statement.executeUpdate(recordTable);
            statement.executeUpdate(issuesTable);

            Logger.getLogger(getClass().getName()).log(Level.INFO, "All tables successfully loaded");
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
//            e.printStackTrace();
        }
    }
}
