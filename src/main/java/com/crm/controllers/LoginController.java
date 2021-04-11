package com.crm.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.crm.service.UserService;
import com.crm.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button registerBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    void initialize() {
        loginBtn.setOnAction(event -> {
            String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();

            if (!loginText.equals("") || !passwordText.equals("")) {
                loginUser(loginText, passwordText);
            } else
                System.out.println("Login or password is empty");
        });

        registerBtn.setOnAction(event -> {
            registerBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/register.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }

    private void loginUser(String loginText, String passwordText) {
        UserService userService = new UserServiceImpl();
        boolean loggedIn = userService.login(loginText, passwordText);
        if (loggedIn) {
            loginBtn.setOnAction(event -> {
                loginBtn.getScene().getWindow().hide();
                String url;
                FXMLLoader loader = new FXMLLoader();
                if (loginText.equals("admin@admin.com")) {
                    url = "/view/user_menu.fxml";
                } else {
                    url = "/view/admin_menu.fxml";
                }
                loader.setLocation(getClass().getResource(url));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            });
        }
    }


//    private void loginUser(String loginText, String passwordText) throws SQLException {
//        DatabaseHandler dbHandler = new DatabaseHandler();
//        User user = User.builder()
//                .email(loginText)
//                .password(passwordText)
//                .build();
//        ResultSet resSet = dbHandler.getUser(user);
//
//        int counter = 0;
//        while (resSet.next()) {
//            counter++;
//        }
//        if (counter >= 1) {
//            System.out.println("Login success!");
//        }
//
//    }
}
