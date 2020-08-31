/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/9/2020
 *  Time: 7:30 PM
 */
package com.inventorymanagement.java.utils;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;

public class Alerts {
    public static void jfxAlertShowAndWait(String title, String body) {
        JFXAlert alert = new JFXAlert();
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(new Label(body));

        JFXButton closeButton = new JFXButton("Close");

        closeButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                alert.hide();
            }
        });

        closeButton.setOnAction(event -> {
            alert.hideWithAnimation();
        });

        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.showAndWait();
    }

    public static void jfxAlert(String title, String body) {
        JFXAlert alert = new JFXAlert();
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(new Label(body));

        JFXButton closeButton = new JFXButton("Close");

        closeButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                alert.hide();
            }
        });

        closeButton.setOnAction(event -> {
            alert.hideWithAnimation();
        });

        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }


    public static void jfxBluredAlert(Node node, Node bluredNode, String title, String body) {
        BoxBlur blur = new BoxBlur(3.0, 3.0, 3);

        JFXAlert alert = new JFXAlert(node.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(new Label(body));

        JFXButton closeButton = new JFXButton("Close");
        closeButton.getStyleClass().add("dialog-accept");

        closeButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                alert.hide();
                bluredNode.setEffect(null);
            }
        });

        closeButton.setOnAction(event -> {
            alert.hideWithAnimation();
            bluredNode.setEffect(null);
        });

        layout.setActions(closeButton);
        alert.setContent(layout);
        bluredNode.setEffect(blur);

        alert.setOnCloseRequest(event -> {
            bluredNode.setEffect(null);
        });
        alert.show();
    }
}
