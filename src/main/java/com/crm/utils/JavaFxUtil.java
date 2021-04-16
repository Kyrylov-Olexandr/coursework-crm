package com.crm.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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

    public static ArrayList<Node> getAllNodes(String url) {
        Parent root = getParentRootByUrl(url);
        ArrayList<Node> nodes = new ArrayList<>();
        assert root != null;
        addAllDescendents(root, nodes);
        return nodes;
    }

    private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            nodes.add(node);
            if (node instanceof Parent)
                addAllDescendents((Parent)node, nodes);
        }
    }

    public static Parent getParentRootByUrl(String url) {
        FXMLLoader loader = new FXMLLoader(JavaFxUtil.class.getResource(url));
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
