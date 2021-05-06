package com.crm.controllers.user_tabs;


import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.crm.controllers.LoginController;
import com.crm.entities.Order;
import com.crm.entities.OrderItem;
import com.crm.entities.Product;
import com.crm.entities.User;
import com.crm.service.OrderService;
import com.crm.service.UserService;
import com.crm.service.impl.OrderServiceImpl;
import com.crm.service.impl.UserServiceImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class UserOrdersTabController {

   @FXML private ResourceBundle resources;
   @FXML private URL location;

    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, String> orderStatusCol;
    @FXML private TableColumn<Order, String> orderIdCol;
    @FXML private TableColumn<Order, String> createdDateCol;

    @FXML private TableView<OrderItem> orderItemTable;
    @FXML private TableColumn<OrderItem, String> productPriceCol;
    @FXML private TableColumn<OrderItem, String> productMeasureCol;
    @FXML private TableColumn<OrderItem, String> productPriceSumCol;
    @FXML private TableColumn<OrderItem, String> productIdCol;
    @FXML private TableColumn<OrderItem, String> productQuantityCol;
    @FXML private TableColumn<OrderItem, String> productNameCol;

   @FXML private TextField orderSumField;

    @FXML private Button showOrderItemsBtn;
    @FXML private Button cancelOrderBtn;

    private final OrderService ORDER_SERVICE = new OrderServiceImpl();
    private final User USER = LoginController.getLoggedInUser();
    private Order selectedOrder;

    @FXML
    void initialize() {
        setupOrdersTableCellsValueFactory();
        setupOrderItemsTableCellsValueFactory();

        fillOrderTable();

        cancelOrderBtn.setOnAction(event -> cancelOrder());
        showOrderItemsBtn.setOnAction(event -> showOrderItems());

    }

    private void fillOrderItemsTable() {
        List<OrderItem> orderItems = selectedOrder.getOrderItems();
        ObservableList<OrderItem> orderItemsData = FXCollections.observableArrayList(orderItems);
        orderItemTable.setItems(orderItemsData);
    }

    private void setSelectedOrder() {
        selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
    }

    private void fillOrderTable() {
        ObservableList<Order> ordersData = FXCollections.observableArrayList(USER.getOrders());
        ordersTable.setItems(ordersData);
    }

    private void setupOrdersTableCellsValueFactory() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        createdDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
    }

    private void setupOrderItemsTableCellsValueFactory() {
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productMeasureCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getMeasure()));
        productPriceCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getPrice().toString()));
        productPriceSumCol.setCellValueFactory(new PropertyValueFactory<>("sum"));
    }

    private void showOrderItems() {
        setSelectedOrder();
        fillOrderItemsTable();
        orderSumField.setText(ORDER_SERVICE.getOrderItemsTotalSum(selectedOrder).toString());
    }

    private void cancelOrder() {
        setSelectedOrder();
        selectedOrder.setStatus("CANCELED");
        ORDER_SERVICE.update(selectedOrder);
        fillOrderTable();
    }

}
