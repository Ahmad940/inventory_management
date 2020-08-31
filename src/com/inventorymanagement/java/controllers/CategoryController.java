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
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryController {
    @FXML
    private StackPane primaryPane;
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
        ProgressBar progressBar = new ProgressBar();
        // initializing list objects
        categoryList = FXCollections.observableArrayList();

        // fetching the list from database
        getCategoryList = categoriesDB.getAllCategories();

        // setting table objects
        setTable();

        // set events
        setBtnEvents();

        // setting the stage draggable
        setStageDraggable();
    }

    private void setBtnEvents() {
        // event for add btn
        addBtn.setOnMouseClicked(event -> {

            BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
            mainPane.setEffect(blur);

            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(primaryPane, content, JFXDialog.DialogTransition.TOP);
            content.setAlignment(Pos.CENTER);
            content.setHeading(new Text("Add Category"));
            dialog.setOverlayClose(false);
            VBox box = new VBox();
            box.setSpacing(15);
            box.setAlignment(Pos.CENTER);

            JFXTextField categoryNameField = new JFXTextField();
            categoryNameField.setPromptText("Category Name");
            categoryNameField.setLabelFloat(true);

            JFXTextField categoryDescriptionField = new JFXTextField();
            categoryDescriptionField.setPromptText("Category Description");
            categoryDescriptionField.setLabelFloat(true);

            box.getChildren().addAll(categoryNameField, categoryDescriptionField);
            box.setSpacing(30.0);
            content.setBody(box);

            JFXButton saveBtn = new JFXButton("Save");
            JFXButton cancelBtn = new JFXButton("Cancel");

            saveBtn.getStyleClass().add("dial-btn");
            cancelBtn.getStyleClass().add("dial-btn");


            saveBtn.setOnAction(event1 -> {
                if (categoryNameField.getText().isEmpty() || categoryNameField.getText().trim().isEmpty()) {
                    Alerts.jfxAlert("Error", "Product Name Field cannot be empty");
                    return;
                }

                Category category = new Category(0, categoryNameField.getText(), categoryDescriptionField.getText());

                if (categoriesDB.addCategory(category) != 1) {
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

        // editing for edit btn
        menuEditBtn.setOnAction(event -> {
            if (tableView.getSelectionModel().getFocusedIndex() == -1) {
                Alerts.jfxAlert("Error", "No item selected");
                return;
            }

            List<RecursiveCategory> selectedProduct = new ArrayList<>();
            selectedProduct.add(tableView.getSelectionModel().getSelectedItem().getValue());

            BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
            mainPane.setEffect(blur);

            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(primaryPane, content, JFXDialog.DialogTransition.TOP);
            content.setAlignment(Pos.CENTER);
            content.setHeading(new Text("Edit Category"));
            dialog.setOverlayClose(false);
            VBox box = new VBox();
            box.setSpacing(15);
            box.setAlignment(Pos.CENTER);

            JFXTextField categoryIdField = new JFXTextField();
            categoryIdField.setPromptText("Category Id");
            categoryIdField.setLabelFloat(true);
            categoryIdField.setDisable(true);
            categoryIdField.setText(tableView.getSelectionModel().getSelectedItem().getValue().getId());

            JFXTextField categoryNameField = new JFXTextField();
            categoryNameField.setPromptText("Category Name");
            categoryNameField.setLabelFloat(true);
            categoryNameField.setText(tableView.getSelectionModel().getSelectedItem().getValue().getCategoryName());

            JFXTextField categoryDescriptionField = new JFXTextField();
            categoryDescriptionField.setPromptText("Category Description");
            categoryDescriptionField.setLabelFloat(true);
            categoryDescriptionField.setText(tableView.getSelectionModel().getSelectedItem().getValue().getCategoryDescription());

            box.getChildren().addAll(categoryIdField, categoryNameField, categoryDescriptionField);
            box.setSpacing(30.0);
            content.setBody(box);

            JFXButton saveBtn = new JFXButton("Save");
            JFXButton cancelBtn = new JFXButton("Cancel");

            saveBtn.getStyleClass().add("dial-btn");
            cancelBtn.getStyleClass().add("dial-btn");


            saveBtn.setOnAction(event1 -> {
                if (categoryNameField.getText().isEmpty() || categoryNameField.getText().trim().isEmpty()) {
                    Alerts.jfxAlert("Error", "Product Name Field cannot be empty");
                    return;
                }

                if (categoriesDB.editCategory(Integer.parseInt(categoryIdField.getText()),
                        categoryNameField.getText(), categoryDescriptionField.getText()) != 1) {
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

        // deleting event
        menuDeleteBtn.setOnAction(event -> {
            if (tableView.getSelectionModel().getFocusedIndex() == -1) {
                Alerts.jfxAlert("Error", "No item selected");
                return;
            }

            int categoryId = Integer.parseInt(tableView.getSelectionModel().getSelectedItem().getValue().getId());
            BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
            mainPane.setEffect(blur);

            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(primaryPane, content, JFXDialog.DialogTransition.TOP);
            content.setAlignment(Pos.CENTER);
            content.setHeading(new Text("Delete Category"));
            dialog.setOverlayClose(false);
            VBox box = new VBox();
            box.setSpacing(15);
            box.setAlignment(Pos.CENTER);

            Label text = new Label("Are you sure you want to delete this item ?");

            box.getChildren().addAll(text);
            box.setSpacing(30.0);
            content.setBody(box);

            JFXButton saveBtn = new JFXButton("Ok");
            JFXButton cancelBtn = new JFXButton("Cancel");

            saveBtn.getStyleClass().add("dial-btn");
            cancelBtn.getStyleClass().add("dial-btn");
            saveBtn.setOnAction(event1 -> {


                if (categoriesDB.deleteCategory(categoryId) != 1) {
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

    public void refreshAction() {
        // event for refresh
        categoryList.removeAll(categoryList);
        getCategoryList = categoriesDB.getAllCategories();
        getCategoryList.forEach(categories -> {
            categoryList.add(new RecursiveCategory(String.valueOf(categories.getId()), categories.getCategoryName(),
                    categories.getCategoryDescription()));
        });
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
