package com.crm.controllers.admin_tabs;

import java.net.URL;
import java.util.*;

import com.crm.controllers.objects.Search;
import com.crm.entities.User;
import com.crm.service.UserService;
import com.crm.service.impl.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class AdvertisingTabController {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> userIdCol;
    @FXML private TableColumn<User, String> userFirstNameCol;
    @FXML private TableColumn<User, String> userLastNameCol;
    @FXML private TableColumn<User, String> userCompanyNameCol;
    @FXML private TableColumn<User, String> userCityCol;
    @FXML private TableColumn<User, String> userEmailCol;

    @FXML private TextField searchByUserIdField;
    @FXML private TextField searchField;
    @FXML private TextField titleField;

    @FXML private Button clearBtn;
    @FXML private Button sendBtn;
    @FXML private Button selectAllBtn;

    @FXML private ResourceBundle resources;
    @FXML private URL location;


    @FXML private TextArea textField;

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private final UserService USER_SERVICE = new UserServiceImpl();
    private Search<User> search;

    @FXML
    void initialize() {
        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setupUserTableCellsValueFactory();

        fillUserTable();

        search = new Search<>(userTable);

        setupDynamicUserSearch();
        setupSearchByUserId();

        sendBtn.setOnAction(event -> {
            try {
                sendMail(getSelectedRecipients());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        selectAllBtn.setOnAction(event -> selectAll());
    }

    private void selectAll() {
        userTable.getSelectionModel().selectAll();
    }

    private Set<String> getSelectedRecipients() {
        Set<String> recipients = new HashSet<>();
        ObservableList<User> selectedUsers = userTable.getSelectionModel().getSelectedItems();
        selectedUsers.forEach(user -> recipients.add(user.getEmail()));
        return recipients;
    }

    public void sendMail(Set<String> recipients) throws Exception {

        System.out.println("Preparing to send email");

        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = emailField.getText();
        //Your gmail password
        String password = passwordField.getText();

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        for (String recipient : recipients) {
            Message message = prepareMessage(session, myAccountEmail, recipient);
            Transport.send(message);
        }
        System.out.println("Message sent successfully");
    }

    private Message prepareMessage(Session session, String myAccountEmail, String recipient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(titleField.getText());
            String htmlCode = "<p>" + textField.getText() + "</p>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
        }
        return null;
    }

    private void setupUserTableCellsValueFactory() {
        ObservableList<TableColumn<User, ?>> columns = userTable.getColumns();
        String[] properties = new String[]{"id", "firstName", "lastName", "companyName", "city", "email"};
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCellValueFactory(new PropertyValueFactory<>(properties[i]));
        }
    }

    private void fillUserTable() {
        ObservableList<User> usersData = FXCollections.observableArrayList(USER_SERVICE.findAll());
        userTable.setItems(usersData);
    }

    private void setupDynamicUserSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                search.findByEntityFields(newValue)
        );
        setData(search.getFilteredData());
    }

    private void setupSearchByUserId() {
        searchByUserIdField.textProperty().addListener((observable, oldValue, newValue) ->
                search.findByFieldName("userId", newValue)
        );
        setData(search.getFilteredData());
    }

    private void setData(FilteredList<User> filteredData) {
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(userTable.comparatorProperty());
        userTable.setItems(sortedData);
    }
}


