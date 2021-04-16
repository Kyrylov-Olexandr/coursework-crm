package com.crm.controllers.admin_tabs;

import com.crm.entities.User;
import com.crm.service.UserService;
import com.crm.service.impl.UserServiceImpl;
import com.crm.utils.JavaFxUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientsTabController {

    @FXML
    private Button refreshUserTableBtn;
    @FXML
    private Button findUserBtn;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField clientFirstNameSearch;
    @FXML
    private TextField clientIdSearch;
    @FXML
    private TextField clientLastNameSearch;
    @FXML
    private TextField clientCompanySearch;
    @FXML
    private TextField clientCitySearch;
    @FXML
    private TextField clientPhoneSearch;
    @FXML
    private TextField clientEmailSearch;
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
        findUserBtn.setOnAction(event -> search());
        search();
    }

    private void refreshUserTable() {

    }

    private void fillTable() {
        ObservableList<TableColumn<User, ?>> columns = clientsTable.getColumns();
        String[] properties = new String[]{"id", "firstName", "lastname", "companyName", "city", "phone", "email", "createdDate"};
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCellValueFactory(new PropertyValueFactory<>(properties[i]));
        }
        clientsTable.setItems(getUserList());
    }

    private ObservableList<User> getUserList() {
        return FXCollections.observableArrayList(USER_SERVICE.findAll());
    }

    private void search() {
//        String clientId = clientIdSearch.getText();
//        String clientFirstName = clientFirstNameSearch.getText();
//        String clientLastName = clientLastNameSearch.getText();
//        String clientCompany = clientCompanySearch.getText();
//        String clientCity = clientCitySearch.getText();
//        String clientPhone = clientPhoneSearch.getText();
//        String clientEmail = clientEmailSearch.getText();

        FilteredList<User> filteredData = new FilteredList<>(clientsTable.getItems(), p -> true);

        clientIdSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> isSearchTextEmpty(newValue) || String.valueOf(user.getId()).equals(newValue));
        });
        setFilteredData(filteredData);

        clientFirstNameSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> isSearchTextEmpty(newValue) || user.getFirstName().contains(newValue));
        });
        setFilteredData(filteredData);

        clientLastNameSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> isSearchTextEmpty(newValue) || user.getLastName().contains(newValue));
        });
        setFilteredData(filteredData);

        clientCompanySearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> isSearchTextEmpty(newValue) || user.getCompanyName().contains(newValue));
        });
        setFilteredData(filteredData);

        clientCitySearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> isSearchTextEmpty(newValue) || user.getCity().contains(newValue));
        });
        setFilteredData(filteredData);

        clientPhoneSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> isSearchTextEmpty(newValue) || user.getPhone().contains(newValue));
        });
        setFilteredData(filteredData);
        clientEmailSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> isSearchTextEmpty(newValue) || user.getEmail().contains(newValue));
        });
        setFilteredData(filteredData);


    }

    private boolean isSearchTextEmpty(String value) {
        return value == null || value.isEmpty();
    }
    private void setFilteredData(FilteredList<User> filteredData) {
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(clientsTable.comparatorProperty());
        clientsTable.setItems(sortedData);
    }
}
//    private void findByCriteria() {
//        List<TextField> criterias = Stream.of(
//                clientIdSearch,
//                clientFirstNameSearch,
//                clientLastNameSearch,
//                clientCompanySearch,
//                clientCitySearch,
//                clientPhoneSearch,
//                clientEmailSearch
//                .collect(Collectors.toList());
//        List<Node> searchFields = JavaFxUtil.getAllNodes("/view/admin_tabs/clientsTab.fxml")
//                .stream()
//                .filter(node -> node instanceof TextField)
//                .collect(Collectors.toList());
//
//        FilteredList<User> filteredData = new FilteredList<>(clientsTable.getItems(), p -> true);
//        filteredData.setPredicate(user -> {
//            searchFields.
//        });
//    }


//    private void setFilteredData(FilteredList<User> filteredData, TextField searchField, String userField) {
//
//        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredData.setPredicate(user -> {
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//                return String.valueOf(userField.contains(newValue);
//            });
//        });
//        SortedList<User> sortedData = new SortedList<>(filteredData);
//        sortedData.comparatorProperty().bind(clientsTable.comparatorProperty());
//        clientsTable.setItems(sortedData);
//    }






