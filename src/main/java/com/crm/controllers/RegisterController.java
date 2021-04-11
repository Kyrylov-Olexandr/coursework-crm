package com.crm.controllers;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import com.crm.dao.impl.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.crm.models.User;

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



    @FXML
    void initialize() {
        UserDao userDaoImpl = new UserDao();
        registerBtn.setOnAction(event -> userDaoImpl.save(createUser()));
    }

    private User createUser() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String city = lastNameField.getText();
        Timestamp currDate = new Timestamp(System.currentTimeMillis());
        return new User.Builder(email,password,firstName,lastName,city,currDate).build();

    }
}
