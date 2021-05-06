package com.crm.controllers.user_tabs;

import java.net.URL;
import java.util.ResourceBundle;

import com.crm.controllers.LoginController;
import com.crm.controllers.objects.Search;
import com.crm.entities.Product;
import com.crm.entities.User;
import com.crm.service.UserService;
import com.crm.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserCabinetTabController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private TextField locationField;
    @FXML private TextField companyField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField lastNameField;
    @FXML private TextField firstNameField;
    @FXML private TextField phoneField;

    @FXML private Button saveBtn;
   
    private final UserService USER_SERVICE = new UserServiceImpl();
    private final User USER = LoginController.getLoggedInUser();


    @FXML
    void initialize() {
        fillUserFields();
        saveBtn.setOnAction(event -> save());
    }

    private void save() {
        User user = USER;
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setCompanyName(companyField.getText());
        user.setPhone(phoneField.getText());
        user.setCity(locationField.getText());
        user.setPassword(passwordField.getText());
        user.setEmail(emailField.getText());
        USER_SERVICE.update(user);
    }

    private void fillUserFields() {
        locationField.setText(USER.getCity());
        companyField.setText(USER.getCompanyName());
        emailField.setText(USER.getEmail());
        passwordField.setText(USER.getPassword());
        lastNameField.setText(USER.getLastName());
        firstNameField.setText(USER.getFirstName());
        phoneField.setText(USER.getPhone());
    }
}
