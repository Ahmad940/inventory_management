/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/9/2020
 *  Time: 4:20 PM
 */
package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao.ProductsDB;
import com.inventorymanagement.java.main.Launcher;
import com.inventorymanagement.java.models.Product;
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

public class MainController {
    double xOffset;
    double yOffset;
    List<Product> getProductsList = null;
    ObservableList<RecursiveProduct> productList = null;
    ProductsDB productsDB = new ProductsDB();
    @FXML
    private MenuItem menuEditBtn;
    @FXML
    private MenuItem menuDeleteBtn;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton refreshBtn;
    @FXML
    private JFXButton issuePurchaseBtn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private TreeTableColumn<RecursiveProduct, String> col_description;
    @FXML
    private TreeTableColumn<RecursiveProduct, String> col_quantity;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TreeTableColumn<RecursiveProduct, String> col_category;
    @FXML
    private TreeTableColumn<RecursiveProduct, String> col_price;
    @FXML
    private TreeTableColumn<RecursiveProduct, String> col_name;
    @FXML
    private TreeTableColumn<RecursiveProduct, String> col_id;
    @FXML
    private JFXTreeTableView<RecursiveProduct> tableView;

    public void initialize() {
        productList = FXCollections.observableArrayList();
        getProductsList = productsDB.getAllProducts();

        setTable();
        setStageDraggable();
    }

    // setting table values
    private void setTable() {
        col_id.setCellValueFactory(param -> param.getValue().getValue().id);
        col_name.setCellValueFactory(param -> param.getValue().getValue().productName);
        col_description.setCellValueFactory(param -> param.getValue().getValue().productDescription);
        col_price.setCellValueFactory(param -> param.getValue().getValue().productPrice);
        col_quantity.setCellValueFactory(param -> param.getValue().getValue().noInStock);
        col_category.setCellValueFactory(param -> param.getValue().getValue().productCategory);

        getProductsList.forEach(product -> {
            productList.add(new RecursiveProduct(String.valueOf(product.getId()), product.getProductName(),
                    String.valueOf(product.getPrice()), product.getProductDescription(),
                    String.valueOf(product.getNoInStock()), product.getProductCategory()));
        });

        TreeItem<RecursiveProduct> root = new RecursiveTreeItem<>(productList, RecursiveTreeObject::getChildren);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setRoot(root);
        tableView.setShowRoot(false);

        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                tableView.setPredicate(modelTreeItem ->
                        modelTreeItem.getValue().id.getValue().toLowerCase().contains(newValue)
                                | modelTreeItem.getValue().productName.getValue().toLowerCase().contains(newValue)));
    }

    // setting stage draggable
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

    class RecursiveProduct extends RecursiveTreeObject<RecursiveProduct> {
        private StringProperty id, productName, productPrice, productDescription, noInStock, productCategory;

        public RecursiveProduct(String id, String productName, String productPrice, String productDescription, String noInStock, String productCategory) {
            this.id = new SimpleStringProperty(id);
            this.productName = new SimpleStringProperty(productName);
            this.productPrice = new SimpleStringProperty(productPrice);
            this.productDescription = new SimpleStringProperty(productDescription);
            this.noInStock = new SimpleStringProperty(noInStock);
            this.productCategory = new SimpleStringProperty(productCategory);
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

        public String getNoInStock() {
            return noInStock.get();
        }

        public void setNoInStock(String noInStock) {
            this.noInStock.set(noInStock);
        }

        public StringProperty noInStockProperty() {
            return noInStock;
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
    }
}