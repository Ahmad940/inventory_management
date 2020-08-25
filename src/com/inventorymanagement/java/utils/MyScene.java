/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/25/2020
 *  Time: 4:20 PM
 */
package com.inventorymanagement.java.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class MyScene {
    public static Scene getScene(Parent node) {
        Scene scene = new Scene(node);
        scene.getStylesheets().add(MyScene.class.getResource(Constants.STYLESHEET_DIR).toExternalForm());
        return scene;
    }
}
