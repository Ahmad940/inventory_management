/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/25/2020
 *  Time: 3:48 PM
 */
package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao.RecordsDB;
import com.inventorymanagement.java.main.Launcher;
import com.inventorymanagement.java.models.Record;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.MyScene;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoryController {
    RecordsDB recordsDB = new RecordsDB();
    List<Record> getRecordList = null;
    ObservableList<RecursiveHistory> recordList = null;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_action;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_date;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_description;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_category;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_price;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_name;

    double xOffset;
    double yOffset;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TreeTableColumn<RecursiveHistory, String> col_id;
    @FXML
    private JFXTreeTableView<RecursiveHistory> tableView;
    @FXML
    private JFXTextField searchField;

    public void initialize() {
        recordList = FXCollections.observableArrayList();
        getRecordList = recordsDB.getAllRecord();

        //set table values
        setTable();
        //set stage draggable
        setStageDraggable();
    }

    public void refreshAction() {
        // event for refresh
        recordList.removeAll(recordList);
        getRecordList = recordsDB.getAllRecord();
//        String.valueOf(record.getId()), record.getProductName(),
//                String.valueOf(record.getPrice()), record.getProductDescription(),
//                String.valueOf(record.getNoInStock()), record.getProductCategory())
        getRecordList.forEach(record -> {
            recordList.add(new RecursiveHistory(String.valueOf(record.getId()), record.getProductName(),
                    String.valueOf(record.getProductPrice()), record.getDescription(),
                    record.getProductCategory(), record.getAction(), record.getDate()
            ));
        });
    }

    // setting table values
    private void setTable() {
        col_id.setCellValueFactory(param -> param.getValue().getValue().id);
        col_name.setCellValueFactory(param -> param.getValue().getValue().productName);
        col_description.setCellValueFactory(param -> param.getValue().getValue().productDescription);
        col_price.setCellValueFactory(param -> param.getValue().getValue().productPrice);
        col_category.setCellValueFactory(param -> param.getValue().getValue().productCategory);
        col_action.setCellValueFactory(param -> param.getValue().getValue().action);
        col_date.setCellValueFactory(param -> param.getValue().getValue().date);


        getRecordList.forEach(record -> {
            recordList.add(new RecursiveHistory(String.valueOf(record.getId()), record.getProductName(),
                    String.valueOf(record.getProductPrice()), record.getDescription(),
                    record.getProductCategory(), record.getAction(),
                    LocalDateTime.parse(record.getDate()).format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss"))
            ));
        });

        TreeItem<RecursiveHistory> root = new RecursiveTreeItem<>(recordList, RecursiveTreeObject::getChildren);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setRoot(root);
        tableView.setShowRoot(false);

        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                tableView.setPredicate(modelTreeItem ->
                        modelTreeItem.getValue().id.getValue().toLowerCase().contains(newValue)
                                | modelTreeItem.getValue().productName.getValue().toLowerCase().contains(newValue)));
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

    class RecursiveHistory extends RecursiveTreeObject<RecursiveHistory> {
        private StringProperty id, productName, productPrice, productDescription, productCategory, action, date;

        public RecursiveHistory(
                String id, String productName,
                String productPrice, String productDescription,
                String productCategory,
                String action, String date
        ) {
            this.id = new SimpleStringProperty(id);
            this.productName = new SimpleStringProperty(productName);
            this.productPrice = new SimpleStringProperty(productPrice);
            this.productDescription = new SimpleStringProperty(productDescription);
            this.productCategory = new SimpleStringProperty(productCategory);
            this.action = new SimpleStringProperty(action);
            this.date = new SimpleStringProperty(date);
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

        public String getProductName() {
            return productName.get();
        }

        public void setProductName(String productName) {
            this.productName.set(productName);
        }

        public StringProperty productNameProperty() {
            return productName;
        }

        public String getProductPrice() {
            return productPrice.get();
        }

        public void setProductPrice(String productPrice) {
            this.productPrice.set(productPrice);
        }

        public StringProperty productPriceProperty() {
            return productPrice;
        }

        public String getProductDescription() {
            return productDescription.get();
        }

        public void setProductDescription(String productDescription) {
            this.productDescription.set(productDescription);
        }

        public StringProperty productDescriptionProperty() {
            return productDescription;
        }

        public String getProductCategory() {
            return productCategory.get();
        }

        public void setProductCategory(String productCategory) {
            this.productCategory.set(productCategory);
        }

        public StringProperty productCategoryProperty() {
            return productCategory;
        }

        public String getAction() {
            return action.get();
        }

        public void setAction(String action) {
            this.action.set(action);
        }

        public StringProperty actionProperty() {
            return action;
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public StringProperty dateProperty() {
            return date;
        }
    }
}
