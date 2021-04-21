package com.crm.controllers;

import com.crm.entities.User;
import com.crm.service.UserService;
import com.crm.service.impl.UserServiceImpl;
import com.crm.utils.HibernateUtil;
import com.crm.utils.JavaFxUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField repPasswordField;

    @FXML
    private Button registerBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField cityField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    private final String LOGIN_WINDOW_URL = "/view/login.fxml";

    private final UserService USER_SERVICE = new UserServiceImpl();

    @FXML
    void initialize() {
        registerBtn.setOnAction(event -> USER_SERVICE.register(createUser()));

        loginBtn.setOnAction(event -> JavaFxUtil.openUrl(event, LOGIN_WINDOW_URL));
    }

    private User createUser() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String city = lastNameField.getText();
        Timestamp currDate = new Timestamp(System.currentTimeMillis());
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setCity(city);
        newUser.setCreatedDate(currDate);
        newUser.setRole("USER");
        return newUser;
//        return new User.Builder(email,password,firstName,lastName,city,currDate).build();
    }

}
