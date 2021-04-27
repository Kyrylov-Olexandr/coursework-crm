package com.crm.controllers.admin_tabs;

import com.crm.controllers.objects.Search;
import com.crm.entities.Order;
import com.crm.entities.OrderItem;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrdersTabController {
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> firstNameCol;
    @FXML
    private TableColumn<Order, String> lastNameCol;
    @FXML
    private TableColumn<Order, String> orderStatusCol;
    @FXML
    private TableColumn<Order, String> companyCol;
    @FXML
    private TableColumn<Order, String> emailCol;
    @FXML
    private TableColumn<Order, String> locationCol;
    @FXML
    private TableColumn<Order, String> phoneCol;
    @FXML
    private TableColumn<Order, String> orderIdCol;
    @FXML
    private TableColumn<Order, String> userIdCol;
    @FXML
    private TableColumn<Order, String> createdDateCol;
    @FXML
    private TableView<OrderItem> orderItemTable;
    @FXML
    private TableColumn<OrderItem, String> productIdCol;
    @FXML
    private TableColumn<OrderItem, String> productQuantityCol;
    @FXML
    private TableColumn<OrderItem, String> productPriceCol;
    @FXML
    private TableColumn<OrderItem, String> productNameCol;
    @FXML
    private Button deleteOrderBtn;
    @FXML
    private Button showClientInfoBtn;
    @FXML
    private Button deleteOrderItemBtn;
    @FXML
    private Button updateOrderItemBtn;
    @FXML
    private Button showProductsBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private TextField searchByIdField;
    @FXML
    private TextField searchField;
    @FXML
    private TextField searchByUserIdField;
    @FXML
    private Button saveOrderItemsBtn;
    @FXML
    private ComboBox<String> statusComboBox;


    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    private final UserService USER_SERVICE = new UserServiceImpl();
    private final OrderService ORDER_SERVICE = new OrderServiceImpl();

    @FXML
    void initialize() {
        setupOrdersTableCellsValueFactory();
        setOrderItemsTableCellsValueFactory();
        fillOrderTable();
        showProductsBtn.setOnAction(event -> showSelectedOrderProducts());
        refreshBtn.setOnAction(event -> fillOrderTable());
        deleteOrderBtn.setOnAction(event -> deleteSelectedOrder());
        setupDynamicOrderSearch();
        setupSearchByOrderId();
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

    private void setupOrdersTableCellsValueFactory() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUser().getId())));
        createdDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        locationCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUser().getCity())));
        phoneCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUser().getPhone())));
        emailCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUser().getEmail())));
        locationCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUser().getCity())));
        firstNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUser().getFirstName())));
        lastNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUser().getLastName())));
        companyCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUser().getCompanyName())));
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    private void setOrderItemsTableCellsValueFactory() {
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productPriceCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getPrice().toString()));
    }


    private void setupDynamicOrderSearch() {
        Search<Order> search = new Search<>(ordersTable);
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                search.findByEntityFields(newValue)
        );
        setData(search.getFilteredData());
    }

    private void setupSearchByOrderId() {
        Search<Order> search = new Search<>(ordersTable);
        searchByIdField.textProperty().addListener((observable, oldValue, newValue) ->
                search.findByFieldName("orderId", newValue)
        );
        setData(search.getFilteredData());
    }

//    private void setupStatusComboBox() {
//        statusComboBox.getItems().addAll(
//                "Виконано",
//                "В очікувані",
//                "Відхилено",
//                "Підтверджено"
//        );
//
//        statusComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
//            if (newItem == null) {
//                fillOrderTable();
//            } else if (newItem.contains("Виконано")) {
//
//            }
//        });
//    }

    private void setData(FilteredList<Order> filteredData) {
        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(ordersTable.comparatorProperty());
        ordersTable.setItems(sortedData);
    }
}
