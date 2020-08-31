/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/27/2020
 *  Time: 4:21 PM
 */
package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao.UsersDB;
import com.inventorymanagement.java.io.UserWriter;
import com.inventorymanagement.java.main.Launcher;
import com.inventorymanagement.java.models.User;
import com.inventorymanagement.java.utils.Alerts;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.ShowTrayNotification;
import com.inventorymanagement.java.utils.Validators;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import tray.animations.AnimationType;
import tray.notification.NotificationType;

import java.io.IOException;
import java.sql.SQLException;

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

    public void loginBtnAction(MouseEvent event) throws SQLException {
        if (loginEmailBtn.getText().isEmpty() || loginEmailBtn.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Email Field cannot be empty");
            loginFail();
            return;
        }

        if (!Validators.isValidEmail(loginEmailBtn.getText())) {
            Alerts.jfxAlert("Error", "Invalid email address");
            loginFail();
            return;
        }

        if (!usersDB.authenticate(loginEmailBtn.getText(), DigestUtils.sha1Hex(loginPasswordField.getText()))) {
            Alerts.jfxAlert("Error", "Email or password incorrect");
            return;
        }

        // flushing file to a path
        UserWriter.encodeUserToFile(loginEmailBtn.getText(), loginPasswordField.getText());

        ShowTrayNotification
                .trayNotification("Login in success", "Welcome back",
                        AnimationType.SLIDE, NotificationType.SUCCESS);

        Parent pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource(Constants.MAIN_FXML_DIR));
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
    }

    public void signUpBtnAction() {
        if (firstNameField.getText().isEmpty() || firstNameField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "First Name Field cannot be empty");
            registrationFail();
            return;
        }

        if (lastNameField.getText().isEmpty() || lastNameField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Last Name Field cannot be empty");
            registrationFail();
            return;
        }

        if (emailField.getText().isEmpty() || emailField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Email Field cannot be empty");
            registrationFail();
            return;
        }

        if (!Validators.isValidEmail(emailField.getText())) {
            Alerts.jfxAlert("Error", "Invalid email address");
            registrationFail();
            return;
        }

        if (passwordField.getText().isEmpty() || passwordField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Password Field cannot be empty");
            registrationFail();
            return;
        }

        if (!maleRadio.isSelected()) {
            if (!femaleRadio.isSelected()) {
                Alerts.jfxAlert("Error", "Gender must be selected");
                registrationFail();
                return;
            }
        }

        if (passwordField.getText().length() < 6) {
            Alerts.jfxAlert("Error", "Password must be greater than 6 characters");
            registrationFail();
            return;
        }

        if (usersDB.userExist(emailField.getText()) == 1) {
            Alerts.jfxAlert("Error", "Email already register");
            ShowTrayNotification
                    .trayNotification("Account creation unsuccessful", "Email already registered",
                            AnimationType.SLIDE, NotificationType.ERROR);
            return;
        }

        String getGender = null;

        if (maleRadio.isSelected()) getGender = "Male";
        if (femaleRadio.isSelected()) getGender = "Female";

        User user = new User(
                0, firstNameField.getText(), lastNameField.getText(), emailField.getText(),
                getGender, 0 + "", passwordField.getText()
        );
        if (usersDB.addUser(user) != 1) {
            Alerts.jfxAlert("Error", "Error occured");
            registrationFail();
            return;
        }

        resetSignUpProperties();

        Alerts.jfxAlert("Successfully", "Account was successfully created");

        ShowTrayNotification
                .trayNotification("Account creation success", "Account Successfully created, login now",
                        AnimationType.SLIDE, NotificationType.SUCCESS);

    }

    private void resetSignUpProperties() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
    }

    private void resetLoginProperties() {
        loginEmailBtn.setText("");
        loginPasswordField.setText("");
    }

    private void registrationFail() {
        ShowTrayNotification
                .trayNotification("Account registration fails", "Account creation was unsuccessful",
                        AnimationType.SLIDE, NotificationType.ERROR);
    }

    private void loginFail() {
        ShowTrayNotification
                .trayNotification("Login fails", "Invalid email or password",
                        AnimationType.SLIDE, NotificationType.ERROR);
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
