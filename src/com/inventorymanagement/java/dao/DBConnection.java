/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/13/2020
 *  Time: 11:26 AM
 */
package com.inventorymanagement.java.dao;

import com.inventorymanagement.java.models.Categories;
import com.inventorymanagement.java.models.Products;
import com.inventorymanagement.java.models.Users;
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

    // initializing a null connection object
//    private Connection conn = null;

    public DBConnection() {
        // setting the connection object
//        conn = connection();

        // creates the needed tables
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
                    Users.USER_EMAIL + "` VARCHAR(64) NOT NULL , `" + Users.USER_GENDER + "` VARCHAR(10) NOT NULL ," +
                    " `" + Users.USER_NUMBER + "` VARCHAR(15) NOT NULL , `" + Users.USER_PASSWORD + "` VARCHAR(1024) NOT NULL," +
                    " PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            // the categories table
            String categoriesTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + DBConstants.TABLE_CATEGORIES +
                    "` ( `" + Categories.CATEGORY_ID + "` INT NOT NULL AUTO_INCREMENT , `" + Categories.CATEGORY_NAME +
                    "` VARCHAR(32) NOT NULL ,  `" + Categories.CATEGORY_DESCRIPTION + "` VARCHAR(64) NOT NULL ," +
                    " PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            // the products table
            String productsTable = "CREATE TABLE IF NOT EXISTS `inventorymanagement`.`" + DBConstants.TABLE_PRODUCTS +
                    "` ( `" + Products.PRODUCT_ID + "` INT NOT NULL AUTO_INCREMENT , `" + Products.PRODUCT_NAME + "` VARCHAR(32) NOT NULL , `" +
                    Products.PRODUCT_DESCRIPTION + "` VARCHAR(64) NOT NULL , `" + Products.PRODUCT_PRICE + "` INT NOT NULL , `" +
                    Products.PRODUCT_NUMBER_IN_STOCK + "` INT NOT NULL , " +
                    "PRIMARY KEY (`id`)) ENGINE = InnoDB;";

            statement.executeUpdate(usersTable);
            statement.executeUpdate(productsTable);
            statement.executeUpdate(categoriesTable);

            Logger.getLogger(getClass().getName()).log(Level.INFO, "All tables successfully loaded");
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, e.getMessage(), e);
//            e.printStackTrace();
        }
    }
}
