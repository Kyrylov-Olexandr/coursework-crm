package main.java.com.crm.controllers;
import java.net.URL;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.crm.DatabaseHandler;
import main.java.com.crm.model.User;

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
        DatabaseHandler dbHandler = new DatabaseHandler();
        registerBtn.setOnAction(event -> dbHandler.registerUser(createUser()));
    }

    private User createUser() {
        return User.builder()
                .email(emailField.getText())
                .password(passwordField.getText())
                .firstName(firstNameField.getText())
                .lastName(lastNameField.getText())
                .city(lastNameField.getText())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
