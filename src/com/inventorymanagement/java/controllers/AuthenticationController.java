/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/27/2020
 *  Time: 4:21 PM
 */
package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao.UsersDB;
import com.inventorymanagement.java.main.Launcher;
import com.inventorymanagement.java.utils.Alerts;
import com.inventorymanagement.java.utils.Validators;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class AuthenticationController {
    double xOffset;
    double yOffset;
    UsersDB usersDB = new UsersDB();
    @FXML
    private StackPane primaryPane;
    @FXML
    private VBox mainPane;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private JFXRadioButton maleRadio;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXRadioButton femaleRadio;
    @FXML
    private JFXButton signUpBtn;
    @FXML
    private TextField loginEmailBtn;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private JFXButton loginBtn;

    public void initialize() {

        //setting stage draggable
        setStageDraggable();
    }

    public void loginBtnAction() {
        System.out.println("Hello World from login btn event");
    }

    public void signUpBtnAction() {
        System.out.println("Hello World from sign up btn event");

        if (firstNameField.getText().isEmpty() || firstNameField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "First Name Field cannot be empty");
            return;
        }

        if (lastNameField.getText().isEmpty() || lastNameField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Last Name Field cannot be empty");
            return;
        }

        if (emailField.getText().isEmpty() || emailField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Email Field cannot be empty");
            return;
        }

        if (!Validators.isValidEmail(emailField.getText())) {
            Alerts.jfxAlert("Error", "Invalid email address");
            return;
        }

        if (passwordField.getText().isEmpty() || passwordField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Password Field cannot be empty");
            return;
        }

        if (!maleRadio.isSelected()) {
            if (!femaleRadio.isSelected()) {
                Alerts.jfxAlert("Error", "Gender must be selected");
                return;
            }
        }

        if (passwordField.getText().length() < 6) {
            Alerts.jfxAlert("Error", "Password must be greater than 6 characters");
            return;
        }

        String tilte = "Sign In";
        String message = "Hey you don`t think u will run away";
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        tray.setAnimationType(type);
        tray.setTitle(tilte);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
    }

    private TrayNotification trayNotification() {
        String tilte = "Sign In";
        String message = "Hey you don`t think u will run away";
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        tray.setAnimationType(type);
        tray.setTitle(tilte);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
        return tray;
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
}
