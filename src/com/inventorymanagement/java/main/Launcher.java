/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/9/2020
 *  Time: 4:19 PM
 */
package com.inventorymanagement.java.main;

import com.inventorymanagement.java.utils.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(Constants.HOME_FXML_DIR));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(Constants.STYLESHEET_DIR).toExternalForm());

        primaryStage.setResizable(false);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
