package utils;

import javafx.scene.control.Alert;

public class MessageUtils {

    public static void showError(String message){

        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Error");
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        dialog.show();
    }

    public static void showMessage(String message){

        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Information");
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        dialog.show();
    }
}
