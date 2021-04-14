package com.crm.controllers.admin_tabs;

import com.crm.entities.User;
import com.crm.service.UserService;
import com.crm.service.impl.UserServiceImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.util.Callback;

public class ClientsTabController {

    @FXML private Button refreshUserTableBtn;
    @FXML private Button findUserBtn;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TextField clientFirstNameSearch;
    @FXML private TextField clientIdSearch;
    @FXML private TextField clientLastNameSearch;
    @FXML private TextField clientCompanySearch;
    @FXML private TextField clientCitySearch;
    @FXML private TextField clientPhoneSearch;
    @FXML private TextField clientEmailSearch;
    @FXML private TableColumn<User, String> userIdCol;
    @FXML private TableColumn<User, String> userLastNameCol;
    @FXML private TableColumn<User, String> userCompanyNameCol;
    @FXML private TableColumn<User, String> userCityCol;
    @FXML private TableColumn<User, String> userNumberCol;
    @FXML private TableColumn<User, String> userEmailCol;
    @FXML private TableColumn<User, String> userCreatedDateCol;
    @FXML private TableView<User> clientsTable;
    @FXML private TableColumn<User, String> userFirstNameCol;

    private final UserService USER_SERVICE = new UserServiceImpl();

    @FXML void initialize() {
        refreshUserTableBtn.setOnAction(event -> refreshUserTable());
    }

    private void refreshUserTable() {

    }
}


