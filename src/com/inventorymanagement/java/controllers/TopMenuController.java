/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/25/2020
 *  Time: 3:23 PM
 */
package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.io.UserWriter;
import com.inventorymanagement.java.main.Launcher;
import com.inventorymanagement.java.utils.Constants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TopMenuController {
    double xOffset;
    double yOffset;

    @FXML
    private ImageView log_out_btn;
    @FXML
    private ImageView exit_btn;
    @FXML
    private ImageView maximize_btn;
    @FXML
    private ImageView minimize_btn;
    @FXML
    private ImageView refresh_btn;
    @FXML
    private AnchorPane mainPane;

    public void initialize() {
        mouseEvents();
        setStageDraggable();
    }

    private void setStageDraggable() {
        mainPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        mainPane.setOnMouseDragged(event -> {
            Launcher.stage.setX(event.getScreenX() - xOffset);
            Launcher.stage.setY(event.getScreenY() - yOffset);
            Launcher.stage.setOpacity(0.5f);
        });

        mainPane.setOnMouseReleased(event -> {
            Launcher.stage.setOpacity(1);
        });
    }

    private void mouseEvents() {
        exit_btn.setOnMouseClicked(event -> Platform.exit());
        maximize_btn.setOnMouseClicked(event -> {
            boolean isFullScreen = false;
            if (Launcher.stage.isFullScreen()) {
                Launcher.stage.setFullScreen(false);
            } else {
                Launcher.stage.setFullScreen(true);
            }

        });
        minimize_btn.setOnMouseClicked(event -> Launcher.stage.setIconified(true));
        log_out_btn.setOnMouseClicked(event -> {
            UserWriter.clearCurrentUser();

            Parent pane = null;
            try {
                pane = FXMLLoader.load(getClass().getResource(Constants.AUTH_FXML_DIR));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            Node node = (Node) event.getSource();

            Stage stage = (Stage) node.getScene().getWindow();

            stage.close();
            Scene scene = new Scene(pane);
            scene.getStylesheets().add(getClass().getResource(Constants.STYLESHEET_DIR).toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });
//       menu_btn.setOnMouseClicked(event -> );
//       refresh_btn.setOnMouseClicked(event -> );
    }
}
