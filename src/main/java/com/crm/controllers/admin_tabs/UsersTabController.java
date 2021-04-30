package com.crm.controllers.admin_tabs;

import com.crm.controllers.objects.Search;
import com.crm.entities.Order;
import com.crm.entities.User;
import com.crm.service.UserService;
import com.crm.service.impl.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class UsersTabController {

    public TextField searchField;
    @FXML private Button refreshUserTableBtn;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TextField searchByUserIdField;
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Integer> userIdCol;
    @FXML private TableColumn<User, String> userFirstNameCol;
    @FXML private TableColumn<User, String> userLastNameCol;
    @FXML private TableColumn<User, String> userCompanyNameCol;
    @FXML private TableColumn<User, String> userCityCol;
    @FXML private TableColumn<User, String> userNumberCol;
    @FXML private TableColumn<User, String> userEmailCol;
    @FXML private TableColumn<User, String> userCreatedDateCol;

    private final UserService USER_SERVICE = new UserServiceImpl();
    private Search<User> search;

    @FXML
    void initialize() {
        setupUserTableCellsValueFactory();
        fillUserTable();
        search = new Search<>(userTable);
        setupDynamicUserSearch();
        setupSearchByUserId();
        refreshUserTableBtn.setOnAction(event -> refreshUserTable());
    }

    private void setupUserTableCellsValueFactory() {
        ObservableList<TableColumn<User, ?>> columns = userTable.getColumns();
        String[] properties = new String[]{"id", "firstName", "lastName", "companyName", "city", "phone", "email", "createdDate"};
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCellValueFactory(new PropertyValueFactory<>(properties[i]));
        }
    }

    private void fillUserTable() {
        ObservableList<User> usersData = FXCollections.observableArrayList(USER_SERVICE.findAll());
        userTable.setItems(usersData);
    }

    private void refreshUserTable() {
        fillUserTable();
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




