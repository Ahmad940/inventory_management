/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/25/2020
 *  Time: 3:50 PM
 */
package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao.CategoriesDB;
import com.inventorymanagement.java.main.Launcher;
import com.inventorymanagement.java.models.Category;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.MyScene;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CategoryController {
    List<Category> getCategoryList = null;
    ObservableList<RecursiveCategory> categoryList = null;
    CategoriesDB categoriesDB = new CategoriesDB();
    @FXML
    private MenuItem menuEditBtn;
    @FXML
    private MenuItem menuDeleteBtn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton refreshBtn;
    @FXML
    private TreeTableColumn<RecursiveCategory, String> col_description;


    double xOffset;
    double yOffset;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TreeTableColumn<RecursiveCategory, String> col_name;
    @FXML
    private TreeTableColumn<RecursiveCategory, String> col_id;
    @FXML
    private JFXTreeTableView<RecursiveCategory> tableView;

    public void initialize() {
        // initializing list objects
        categoryList = FXCollections.observableArrayList();

        // fetching the list from database
        getCategoryList = categoriesDB.getAllCategories();

        // setting table objects
        setTable();

        // setting the stage draggable
        setStageDraggable();
    }


    private void setTable() {
        col_id.setCellValueFactory(param -> param.getValue().getValue().id);
        col_name.setCellValueFactory(param -> param.getValue().getValue().categoryName);
        col_description.setCellValueFactory(param -> param.getValue().getValue().categoryDescription);

        getCategoryList.forEach(category -> {
            categoryList.add(new RecursiveCategory(String.valueOf(category.getId()), category.getCategoryName(),
                    category.getCategoryDescription()));
        });

        TreeItem<RecursiveCategory> root = new RecursiveTreeItem<>(categoryList, RecursiveTreeObject::getChildren);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setRoot(root);
        tableView.setShowRoot(false);

        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                tableView.setPredicate(modelTreeItem ->
                        modelTreeItem.getValue().id.getValue().toLowerCase().contains(newValue)
                                | modelTreeItem.getValue().categoryName.getValue().toLowerCase().contains(newValue)));
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

    // product handler
    public void productBtnEvent(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.MAIN_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // issue handler
    public void issueBtnEvent(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.ISSUE_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // category handler
    public void categoryBtnEvent(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.CATEGORY_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // history handler
    public void historyBtnEvent(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.HISTORY_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    class RecursiveCategory extends RecursiveTreeObject<RecursiveCategory> {
        private StringProperty id, categoryName, categoryDescription;

        public RecursiveCategory(String id, String categoryName, String categoryDescription) {
            this.id = new SimpleStringProperty(id);
            this.categoryName = new SimpleStringProperty(categoryName);
            this.categoryDescription = new SimpleStringProperty(categoryDescription);
        }

        public String getId() {
            return id.get();
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public StringProperty idProperty() {
            return id;
        }

        public String getCategoryName() {
            return categoryName.get();
        }

        public void setCategoryName(String categoryName) {
            this.categoryName.set(categoryName);
        }

        public StringProperty categoryNameProperty() {
            return categoryName;
        }

        public String getCategoryDescription() {
            return categoryDescription.get();
        }

        public void setCategoryDescription(String categoryDescription) {
            this.categoryDescription.set(categoryDescription);
        }

        public StringProperty categoryDescriptionProperty() {
            return categoryDescription;
        }
    }
}
