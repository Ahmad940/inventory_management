/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/9/2020
 *  Time: 4:19 PM
 */
package com.inventorymanagement.java.main;

import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.configs.db.DBDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Launcher extends Application {

    DBDataSource dbDataSource = new DBDataSource();
    public static Stage stage = null;
    double xOffset;
    double yOffset;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(Constants.AUTH_FXML_DIR));
//            root = FXMLLoader.load(getClass().getResource(Constants.MAIN_FXML_DIR));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(Constants.STYLESHEET_DIR).toExternalForm());

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.getIcons().add(new Image(Constants.STAGE_ICON));
        primaryStage.setScene(scene);
        primaryStage.show();

        stage = primaryStage;
    }

    @Override
    public void init() throws Exception {
        super.init();
        System.out.println(dbDataSource.getDatasource());
        System.out.println(dbDataSource.getUser());
        System.out.println(dbDataSource.getPassword());
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
