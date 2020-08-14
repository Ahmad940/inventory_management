/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/13/2020
 *  Time: 11:26 AM
 */
package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.models.*;
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
            Logger.getLogger(getClass().getName()).log(Level.INFO, e.getMessage(), e);
            return null;
        }
    }

    // automating table creation
    private void createTables() {
        try {
            Statement statement = connection().createStatement();

            // the users table
            String usersTable = "CREATE TABLE IF NOT EXISTS  `inventorymanagement`.`" + DBConstants.TABLE_USERS +
                    "` ( `" + Users.USER_ID + "` INT NOT NULL AUTO_INCREMENT , `" + Users.USER_FIRST_NAME +
                    "` VARCHAR(32) NOT NULL , `" + Users.USER_LAST_NAME + "` VARCHAR(32) NOT NULL , `" +
                    Users.USER_EMAIL + "` VARCHAR(64) NOT NULL UNIQUE , `" + Users.USER_GENDER + "` VARCHAR(10) NOT NULL ," +
                    " `" + Users.USER_NUMBER + "` VARCHAR(15) NOT NULL , `" + Users.USER_PASSWORD + "` VARCHAR(1024) NOT NULL," +
                    " PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            // the categories table
            String categoriesTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + DBConstants.TABLE_CATEGORIES +
                    "` ( `" + Categories.CATEGORY_ID + "` INT NOT NULL AUTO_INCREMENT , `" + Categories.CATEGORY_NAME +
                    "` VARCHAR(32) NOT NULL ,  `" + Categories.CATEGORY_DESCRIPTION + "` VARCHAR(64) ," +
                    " PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            // the products table
            String productsTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + DBConstants.TABLE_PRODUCTS +
                    "` ( `" + Products.PRODUCT_ID + "` INT NOT NULL AUTO_INCREMENT , `" +
                    Products.PRODUCT_NAME + "` VARCHAR(32) NOT NULL , `" +
                    Products.PRODUCT_DESCRIPTION + "` VARCHAR(64) NOT NULL , `" + Products.PRODUCT_PRICE + "` DOUBLE NOT NULL , `" +
                    Products.PRODUCT_NUMBER_IN_STOCK + "` INT NOT NULL , `" + Products.PRODUCT_CATEGORY +
                    "` VARCHAR(15) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            String recordTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + DBConstants.TABLE_RECORDS +
                    "` ( `" + Records.RECORD_ID + "` INT NOT NULL AUTO_INCREMENT, `" +
                    Records.RECORD_PRODUCT_NAME + "` VARCHAR(32) NOT NULL, `" + Records.RECORD_PRICE +
                    "` DOUBLE NOT NULL, ` " + Records.RECORD_DESCRIPTION + "` VARCHAR(64) NOT NULL, `" +
                    Records.RECORD_CATEGORY + "` VARCHAR(15) NOT NULL, `" + Records.RECORD_DATE + "` " +
                    "VARCHAR(15) NOT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            String issuesTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + DBConstants.TABLE_ISSUES +
                    "` ( `" + Issues.PRODUCT_ID + "` INT NOT NULL AUTO_INCREMENT, `" +
                    Issues.PRODUCT_Name + "` VARCHAR(32) NOT NULL, `" + Issues.PRODUCT_PRICE +
                    "` DOUBLE NOT NULL, ` " + Issues.PRODUCT_DESCRIPTION + "` VARCHAR(64) NOT NULL, `" +
                    Issues.PRODUCT_CATEGORY + "` VARCHAR(15) NOT NULL, `" + Issues.DATE + "` " +
                    "VARCHAR(15) NOT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            statement.executeUpdate(usersTable);
            statement.executeUpdate(productsTable);
            statement.executeUpdate(categoriesTable);
            statement.executeUpdate(recordTable);
            statement.executeUpdate(issuesTable);

            Logger.getLogger(getClass().getName()).log(Level.INFO, "All tables successfully loaded");
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, e.getMessage(), e);
//            e.printStackTrace();
        }
    }
}
