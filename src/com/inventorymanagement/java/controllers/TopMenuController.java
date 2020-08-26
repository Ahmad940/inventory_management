/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/25/2020
 *  Time: 3:23 PM
 */
package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.main.Launcher;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class TopMenuController {
    double xOffset;
    double yOffset;

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
//       menu_btn.setOnMouseClicked(event -> );
//       refresh_btn.setOnMouseClicked(event -> );
    }
}
