package com.crm.controllers.admin_tabs;

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

public class ClientsTabController {

    public TextField searchField;
    @FXML
    private Button refreshUserTableBtn;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField clientIdSearch;
    @FXML
    private TableView<User> clientsTable;
    @FXML
    private TableColumn<User, Integer> userIdCol;
    @FXML
    private TableColumn<User, String> userFirstNameCol;
    @FXML
    private TableColumn<User, String> userLastNameCol;
    @FXML
    private TableColumn<User, String> userCompanyNameCol;
    @FXML
    private TableColumn<User, String> userCityCol;
    @FXML
    private TableColumn<User, String> userNumberCol;
    @FXML
    private TableColumn<User, String> userEmailCol;
    @FXML
    private TableColumn<User, String> userCreatedDateCol;

    private final UserService USER_SERVICE = new UserServiceImpl();

    @FXML
    void initialize() {
        fillTable();
        searchById();
        searchByUserFields();
        refreshUserTable();
    }

    private void refreshUserTable() {
        refreshUserTableBtn.setOnAction(event -> fillTable());
    }

    private void fillTable() {
        ObservableList<TableColumn<User, ?>> columns = clientsTable.getColumns();
        String[] properties = new String[]{"id", "firstName", "lastname", "companyName", "city", "phone", "email", "createdDate"};
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCellValueFactory(new PropertyValueFactory<>(properties[i]));
        }
        clientsTable.setItems(getUserObservableList());
    }

    private ObservableList<User> getUserObservableList() {
        return FXCollections.observableArrayList(USER_SERVICE.findAll());
    }

    private void searchByUserFields() {
        FilteredList<User> filteredData = new FilteredList<>(clientsTable.getItems(), p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createPredicate(newValue))
        );
        setFilteredData(filteredData);
    }

    private boolean searchFindsUser(User user, String searchText){
        return  (user.getFirstName().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getLastName().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getCity().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getEmail().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getCompanyName().toLowerCase().contains((searchText.toLowerCase())) ||
                (user.getPhone().toLowerCase().contains(searchText.toLowerCase()))) ||
                (user.getCreatedDate().toString().contains(searchText.toLowerCase()));
    }

    private Predicate<User> createPredicate(String searchText){
        return user -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsUser(user, searchText);
        };
    }

    private void searchById() {
        FilteredList<User> filteredData = new FilteredList<>(clientsTable.getItems(), p -> true);
        clientIdSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return String.valueOf(user.getId()).equals(newValue);
            });
        });
        setFilteredData(filteredData);
    }


    private void setFilteredData(FilteredList<User> filteredData) {
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(clientsTable.comparatorProperty());
        clientsTable.setItems(sortedData);
    }
}




