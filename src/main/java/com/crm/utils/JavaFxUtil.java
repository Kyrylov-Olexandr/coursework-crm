package com.crm.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFxUtil {

    private JavaFxUtil() {
    }

    public static void openUrl(ActionEvent event, String url) {
        Node node = (Node) event.getSource();
        node.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(JavaFxUtil.class.getResource(url));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
