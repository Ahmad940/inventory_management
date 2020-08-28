/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/25/2020
 *  Time: 3:46 PM
 */
package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao.IssuesDB;
import com.inventorymanagement.java.dao.ProductsDB;
import com.inventorymanagement.java.dao.RecordsDB;
import com.inventorymanagement.java.main.Launcher;
import com.inventorymanagement.java.models.Issue;
import com.inventorymanagement.java.models.Product;
import com.inventorymanagement.java.models.Record;
import com.inventorymanagement.java.utils.Alerts;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.MyScene;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class IssuesController {
    List<Issue> getIssueList = null;
    ObservableList<RecursiveIssue> issueList = null;
    ProductsDB productsDB = new ProductsDB();
    IssuesDB issuesDB = new IssuesDB();
    RecordsDB recordsDB = new RecordsDB();
    @FXML
    private TreeTableColumn<RecursiveIssue, String> col_quantity;
    @FXML
    private TreeTableColumn<RecursiveIssue, String> col_date;
    @FXML
    private TreeTableColumn<RecursiveIssue, String> col_description;
    @FXML
    private TreeTableColumn<RecursiveIssue, String> col_category;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXButton declineBtn;
    @FXML
    private JFXButton purchaseBtn;

    double xOffset;
    double yOffset;
    @FXML
    private TreeTableColumn<RecursiveIssue, String> col_price;
    @FXML
    private TreeTableColumn<RecursiveIssue, String> col_name;
    @FXML
    private TreeTableColumn<RecursiveIssue, String> col_id;
    @FXML
    private JFXTreeTableView<RecursiveIssue> tableView;
    @FXML
    private StackPane primaryPane;
    @FXML
    private AnchorPane mainPane;

    public void initialize() {
        issueList = FXCollections.observableArrayList();
        getIssueList = issuesDB.getAllIssues();

        // setting the stage draggable
        setStageDraggable();
        // setting the table values
        setTable();
        // setting objects attributes
        setBtnActionEvents();
    }

    private void setBtnActionEvents() {
        // purchase action
        purchaseBtn.setOnAction(event -> {
            int productId = Integer.parseInt(tableView.getSelectionModel().getSelectedItem().getValue().getId());
            BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
            mainPane.setEffect(blur);

            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(primaryPane, content, JFXDialog.DialogTransition.TOP);
            content.setAlignment(Pos.CENTER);
            content.setHeading(new Text("Purchase Product"));
            dialog.setOverlayClose(false);
            VBox box = new VBox();
            box.setSpacing(15);
            box.setAlignment(Pos.CENTER);

            Label text = new Label("Are you sure you want to add this item to the inventory ?");

            box.getChildren().addAll(text);
            box.setSpacing(30.0);
            content.setBody(box);

            JFXButton saveBtn = new JFXButton("Ok");
            JFXButton cancelBtn = new JFXButton("Cancel");

            saveBtn.getStyleClass().add("dial-btn");
            cancelBtn.getStyleClass().add("dial-btn");


            saveBtn.setOnAction(event1 -> {
                Record record = new Record(
                        0,
                        Double.parseDouble(tableView.getSelectionModel().getSelectedItem().getValue().getProductPrice()),
                        tableView.getSelectionModel().getSelectedItem().getValue().getProductName(),
                        tableView.getSelectionModel().getSelectedItem().getValue().getProductCategory(),
                        tableView.getSelectionModel().getSelectedItem().getValue().getProductDescription(),
                        "added", LocalDateTime.now().toString()
                );

                if (recordsDB.addRecord(record) != 1) {
                    Alerts.jfxAlert("Error", "An error occurred");
                    return;
                }

                Product product = new Product(
                        0,
                        Integer.parseInt(tableView.getSelectionModel().getSelectedItem().getValue().getNoInStock()),
                        Double.parseDouble(tableView.getSelectionModel().getSelectedItem().getValue().getProductPrice()),
                        tableView.getSelectionModel().getSelectedItem().getValue().getProductName(),
                        tableView.getSelectionModel().getSelectedItem().getValue().getProductDescription(),
                        tableView.getSelectionModel().getSelectedItem().getValue().getProductCategory()
                );

                if (productsDB.addProduct(product) != 1) {
                    Alerts.jfxAlert("Error", "An error occurred");
                    return;
                }
                if (issuesDB.deleteIssue(productId) != 1) {
                    Alerts.jfxAlert("Error", "An error occurred");
                    return;
                }
                refreshAction();
                dialog.close();
            });

            cancelBtn.setOnAction(event1 -> {
                dialog.close();
            });

            content.setActions(saveBtn, cancelBtn);

            dialog.setOnDialogClosed(event1 -> {
                mainPane.setEffect(null);
            });
            dialog.show();
        });

        // decline action
        declineBtn.setOnAction(event -> {
            int productId = Integer.parseInt(tableView.getSelectionModel().getSelectedItem().getValue().getId());
            BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
            mainPane.setEffect(blur);

            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(primaryPane, content, JFXDialog.DialogTransition.TOP);
            content.setAlignment(Pos.CENTER);
            content.setHeading(new Text("Delete Product"));
            dialog.setOverlayClose(false);
            VBox box = new VBox();
            box.setSpacing(15);
            box.setAlignment(Pos.CENTER);

            Label text = new Label("Are you sure you want to discard this item ?");

            box.getChildren().addAll(text);
            box.setSpacing(30.0);
            content.setBody(box);

            JFXButton saveBtn = new JFXButton("Ok");
            JFXButton cancelBtn = new JFXButton("Cancel");

            saveBtn.getStyleClass().add("dial-btn");
            cancelBtn.getStyleClass().add("dial-btn");


            saveBtn.setOnAction(event1 -> {
                Record record = new Record(
                        0,
                        Double.parseDouble(tableView.getSelectionModel().getSelectedItem().getValue().getProductPrice()),
                        tableView.getSelectionModel().getSelectedItem().getValue().getProductName(),
                        tableView.getSelectionModel().getSelectedItem().getValue().getProductCategory(),
                        tableView.getSelectionModel().getSelectedItem().getValue().getProductDescription(),
                        "decline", LocalDateTime.now().toString()
                );
                if (recordsDB.addRecord(record) != 1) {
                    Alerts.jfxAlert("Error", "An error occurred");
                    return;
                }
                if (issuesDB.deleteIssue(productId) != 1) {
                    Alerts.jfxAlert("Error", "An error occurred");
                    return;
                }
                refreshAction();
                dialog.close();
            });

            cancelBtn.setOnAction(event1 -> {
                dialog.close();
            });

            content.setActions(saveBtn, cancelBtn);

            dialog.setOnDialogClosed(event1 -> {
                mainPane.setEffect(null);
            });
            dialog.show();
        });
    }

    // setting table values
    private void setTable() {
        col_id.setCellValueFactory(param -> param.getValue().getValue().id);
        col_name.setCellValueFactory(param -> param.getValue().getValue().productName);
        col_description.setCellValueFactory(param -> param.getValue().getValue().productDescription);
        col_price.setCellValueFactory(param -> param.getValue().getValue().productPrice);
        col_quantity.setCellValueFactory(param -> param.getValue().getValue().noInStock);
        col_category.setCellValueFactory(param -> param.getValue().getValue().productCategory);
        col_date.setCellValueFactory(param -> param.getValue().getValue().date);

        getIssueList.forEach(issue -> {
            issueList.add(new RecursiveIssue(String.valueOf(issue.getId()), issue.getProductName(),
                    String.valueOf(issue.getPrice()), issue.getProductDescription(),
                    String.valueOf(issue.getNumberInStock()), issue.getProductCategory(),
                    LocalDateTime.parse(issue.getDate()).format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss"))));
        });

        TreeItem<RecursiveIssue> root = new RecursiveTreeItem<>(issueList, RecursiveTreeObject::getChildren);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setRoot(root);
        tableView.setShowRoot(false);

        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                tableView.setPredicate(modelTreeItem ->
                        modelTreeItem.getValue().id.getValue().toLowerCase().contains(newValue)
                                | modelTreeItem.getValue().productName.getValue().toLowerCase().contains(newValue)));
    }

    public void refreshAction() {
        // event for refresh
        issueList.removeAll(issueList);
        getIssueList = issuesDB.getAllIssues();
        getIssueList.forEach(issue -> {
            issueList.add(new RecursiveIssue(String.valueOf(issue.getId()), issue.getProductName(),
                    String.valueOf(issue.getPrice()), issue.getProductDescription(),
                    String.valueOf(issue.getNumberInStock()), issue.getProductCategory(), issue.getDate()));
        });
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

    class RecursiveIssue extends RecursiveTreeObject<RecursiveIssue> {
        private StringProperty id, productName, productPrice, productDescription, noInStock, productCategory, date;

        public RecursiveIssue(String id, String productName, String productPrice, String productDescription, String noInStock, String productCategory, String date) {
            this.id = new SimpleStringProperty(id);
            this.productName = new SimpleStringProperty(productName);
            this.productPrice = new SimpleStringProperty(productPrice);
            this.productDescription = new SimpleStringProperty(productDescription);
            this.noInStock = new SimpleStringProperty(noInStock);
            this.productCategory = new SimpleStringProperty(productCategory);
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
