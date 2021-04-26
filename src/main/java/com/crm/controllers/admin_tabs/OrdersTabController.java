package com.crm.controllers.admin_tabs;

import com.crm.entities.Order;
import com.crm.entities.OrderItem;
import com.crm.entities.User;
import com.crm.service.OrderService;
import com.crm.service.UserService;
import com.crm.service.impl.OrderServiceImpl;
import com.crm.service.impl.UserServiceImpl;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class OrdersTabController {
    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, String> locationCol;
    @FXML private TableColumn<Order, String> phoneCol;
    @FXML private TableColumn<Order, String> orderIdCol;
    @FXML private TableColumn<Order, String> userIdCol;
    @FXML private TableColumn<Order, String> createdDateCol;
    @FXML private TableView<OrderItem> orderItemTable;
    @FXML private TableColumn<OrderItem, String> productIdCol;
    @FXML private TableColumn<OrderItem, String> productQuantityCol;
    @FXML private TableColumn<OrderItem, String> productPriceCol;
    @FXML private TableColumn<OrderItem, String> productNameCol;
    @FXML private Button deleteOrderBtn;
    @FXML private Button showClientInfoBtn;
    @FXML private Button deleteOrderItemBtn;
    @FXML private Button updateOrderItemBtn;
    @FXML private Button showProductsBtn;
    @FXML private Button refreshBtn;
    @FXML private TextField searchByIdField;
    @FXML private TextField searchField;
    @FXML private ResourceBundle resources;
    @FXML private URL location;

    private final UserService USER_SERVICE = new UserServiceImpl();
    private final OrderService ORDER_SERVICE = new OrderServiceImpl();

    @FXML
    void initialize() {
        setOrdersTableCellsValueFactory();
        setOrderItemsTableCellsValueFactory();
        fillOrderTable();
        showProductsBtn.setOnAction(event -> showSelectedOrderProducts());
        refreshBtn.setOnAction(event -> fillOrderTable());
        deleteOrderBtn.setOnAction(event -> deleteSelectedOrder());
        searchOrder();
        searchByOrderId();
    }

    private void deleteSelectedOrder() {
        Order order = ordersTable.getSelectionModel().getSelectedItem();
        ORDER_SERVICE.delete(order);
    }

    private void fillOrderTable() {
        ObservableList<Order> ordersData = FXCollections.observableArrayList(ORDER_SERVICE.findAll());
        ordersTable.setItems(ordersData);
    }

    private void showSelectedOrderProducts() {
        Order order = ordersTable.getSelectionModel().getSelectedItem();
        List<OrderItem> orderItems = order.getOrderItems();
        ObservableList<OrderItem> orderItemsData = FXCollections.observableArrayList(orderItems);
        orderItemTable.setItems(orderItemsData);
    }

    private void setOrdersTableCellsValueFactory() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUser().getId())));
        createdDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        locationCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUser().getCity())));
        phoneCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUser().getPhone())));
    }

    private void setOrderItemsTableCellsValueFactory() {
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productPriceCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getPrice().toString()));
    }

    private void searchOrder() {
        FilteredList<Order> filteredData = new FilteredList<>(ordersTable.getItems(), p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createPredicate(newValue))
        );
        setData(filteredData);
    }

    private boolean searchFindsOrder(Order order, String searchText){
        User user = order.getUser();
        return  (String.valueOf(user.getId()).contains(searchText.toLowerCase())) ||
                (user.getFirstName().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getLastName().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getCity().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getEmail().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getCompanyName().toLowerCase().contains((searchText.toLowerCase())) ||
                (user.getPhone().toLowerCase().contains(searchText.toLowerCase()))) ||
                (user.getCreatedDate().toString().contains(searchText.toLowerCase()));
    }

    private Predicate<Order> createPredicate(String searchText){
        return order -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsOrder(order, searchText);
        };
    }

    private void searchByOrderId() {
        FilteredList<Order> filteredData = new FilteredList<>(ordersTable.getItems(), p -> true);
        searchByIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(order -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return String.valueOf(order.getId()).equals(newValue);
            });
        });
        setData(filteredData);
    }


    private void setData(FilteredList<Order> filteredData) {
        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(ordersTable.comparatorProperty());
        ordersTable.setItems(sortedData);
    }
}
