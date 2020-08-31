/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/28/2020
 *  Time: 9:26 AM
 */
package com.inventorymanagement.java.utils;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ShowTrayNotification {
    public static TrayNotification trayNotification(
            String title, String message,
            AnimationType animationType,
            NotificationType notificationType
    ) {
        TrayNotification tray = new TrayNotification();

        tray.setAnimationType(animationType);
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(notificationType);
        tray.showAndDismiss(Duration.millis(3000));
        return tray;
    }
}
