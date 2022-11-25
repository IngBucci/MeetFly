package sample.view;

import javafx.stage.Window;

public class Notifiche {
    public static void infoBox(String infoMessage, String headerText, String title) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert( javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    public static void showAlert(javafx.scene.control.Alert.AlertType alertType, Window owner, String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
