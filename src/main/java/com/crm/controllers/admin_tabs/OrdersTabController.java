package com.crm.controllers.admin_tabs;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.crm.entities.BaseEntity;
import com.crm.entities.Order;
import com.crm.entities.Product;
import com.crm.entities.User;
import com.crm.service.OrderService;
import com.crm.service.UserService;
import com.crm.service.impl.OrderServiceImpl;
import com.crm.service.impl.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrdersTabController {
    @FXML
    private TableColumn<Product, String> productIdCol;
    @FXML
    private TableColumn<Product, String> productQuantityCol;
    @FXML
    private TableColumn<Product, String> productPrice;
    @FXML
    private Button showProductsBtn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchByIdField;

    @FXML
    private TextField searchField;

    @FXML
    private Button refreshBtn;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, String> orderIdCol;

    @FXML
    private TableColumn<User, String> userIdCol;

    @FXML
    private TableColumn<Order, String> createdDateCol;

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, String> productAmountCol;


    private final UserService USER_SERVICE = new UserServiceImpl();
    private final OrderService ORDER_SERVICE = new OrderServiceImpl();

    @FXML
    void initialize() {
        fillOrderTable();
        showProductsBtn.setOnAction(event -> showOrderProducts());
        refreshBtn.setOnAction(event -> fillOrderTable());
    }


    private void fillOrderTable() {
            ObservableList<TableColumn<Order, ?>> columns = ordersTable.getColumns();
            String[] properties = new String[]{"id", "userId", "createdDate"};
            for (int i = 0; i < columns.size(); i++) {
                columns.get(i).setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            }

        ObservableList<Order> orders = FXCollections.observableArrayList(ORDER_SERVICE.findAll());
        ordersTable.setItems(orders);
    }
//    private ObservableList<Product> getProductsObservableArrayList() {
//        List<Product> products = ordersTable.getSelectionModel().getSelectedItem().getProducts();
//        return FXCollections.observableArrayList(products);
//    }
    private void showOrderProducts() {
        List<Product> products = ordersTable.getSelectionModel().getSelectedItem().getProducts();
        ObservableList<Product> productObservableArrayList = FXCollections.observableArrayList(products);
        ObservableList<TableColumn<Product, ?>> productTableColumns = productTable.getColumns();
        String[] properties = new String[]{"id", "name", "quantity", "price"};
        for (int i = 0; i < productTableColumns.size(); i++) {
            productTableColumns.get(i).setCellValueFactory(new PropertyValueFactory<>(properties[i]));
        }
        productTable.setItems(productObservableArrayList);
    }

}
