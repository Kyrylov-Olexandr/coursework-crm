package com.crm.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.crm.entities.User;
import com.crm.exceptions.WrongEmailOrPassException;
import com.crm.service.UserService;
import com.crm.service.impl.UserServiceImpl;
import com.crm.utils.JavaFxUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    private final String REGISTER_WINDOW_URL = "/view/register.fxml";
    private final String USER_MENU_URL = "/view/user_menu.fxml";
    private static final String ADMIN_MENU_URL = "/view/admin_menu.fxml";

    private final UserService USER_SERVICE = new UserServiceImpl();

    private static User loggedInUser;

    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    @FXML
    void initialize() {
        loginBtn.setOnAction(this::loginUser);

        registerBtn.setOnAction(event -> JavaFxUtil.openUrl(event, REGISTER_WINDOW_URL));
    }

    private void loginUser(ActionEvent event) {
        try {
            String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();

            Optional<User> userOptional = USER_SERVICE.getByEmailAndPassword(loginText, passwordText);

            if (userOptional.isPresent()) {
                boolean isAdmin = userOptional.get().getRole().equals("ADMIN");
                loggedInUser = userOptional.get();
                JavaFxUtil.openUrl(event, ( isAdmin ? ADMIN_MENU_URL : USER_MENU_URL ));
            } else {
                throw new WrongEmailOrPassException("Wrong email or password!");
            }
        } catch (WrongEmailOrPassException e) {
            LOG.warning(e.getMessage());
        }
    }
}

