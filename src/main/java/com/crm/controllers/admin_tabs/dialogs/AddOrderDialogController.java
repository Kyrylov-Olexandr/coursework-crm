package com.crm.controllers.admin_tabs.dialogs;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.crm.controllers.objects.Search;
import com.crm.entities.Order;
import com.crm.entities.OrderItem;
import com.crm.entities.Product;
import com.crm.entities.User;
import com.crm.service.OrderService;
import com.crm.service.ProductService;
import com.crm.service.UserService;
import com.crm.service.impl.OrderServiceImpl;
import com.crm.service.impl.ProductServiceImpl;
import com.crm.service.impl.UserServiceImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddOrderDialogController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, String> productIdCol;
    @FXML private TableColumn<Product, String > productNameCol;
    @FXML private TableColumn<Product, String> productPriceCol;

    @FXML private TableView<OrderItem> orderItemTable;
    @FXML private TableColumn<OrderItem, String> orderItemProductIdCol;
    @FXML private TableColumn<OrderItem, String> orderItemProductNameCol;
    @FXML private TableColumn<OrderItem, String> orderItemQuantityCol;
    @FXML private TableColumn<OrderItem, String> orderItemPriceCol;

    @FXML private TextField searchByProductIdField;
    @FXML private TextField searchByProductNameField;
    @FXML private TextField quantityField;
    @FXML private TextField userIdField;

    @FXML private Button addOrderItemBtn;
    @FXML private Button removeOrderItemBtn;
    @FXML private Button saveOrderBtn;

    private final UserService USER_SERVICE = new UserServiceImpl();
    private final ProductService PRODUCT_SERVICE = new ProductServiceImpl();
    private final OrderService ORDER_SERVICE = new OrderServiceImpl();

    private final Order NEW_ORDER = new Order();
    private Search<Product> search;

    @FXML
    void initialize() {
        NEW_ORDER.setOrderItems(new ArrayList<>());

        setupOrderItemsTableCellsValueFactory();
        setupProductTableCellsValueFactory();

        fillProductTable();

        search = new Search<>(productsTable);

        setupDynamicProductSearch();
        setupSearchByProductId();

        addOrderItemBtn.setOnAction(event -> addOrderItem());
        removeOrderItemBtn.setOnAction(event -> removeSelectedOrderItem());
        saveOrderBtn.setOnAction(event -> saveOrder());
    }

    private void saveOrder() {
        Optional<User> userOptional = USER_SERVICE.findById(Integer.parseInt(userIdField.getText()));
        if (userOptional.isPresent()) {
            NEW_ORDER.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            NEW_ORDER.setUser(userOptional.get());
            NEW_ORDER.setStatus("PENDING");
            ORDER_SERVICE.save(NEW_ORDER);
        }
    }

    private void setupOrderItemsTableCellsValueFactory() {
        orderItemProductIdCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getProduct().getId())));
        orderItemProductNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        orderItemQuantityCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        orderItemPriceCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getPrice().toString()));
    }

    private void setupProductTableCellsValueFactory() {
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void setupDynamicProductSearch() {
        searchByProductNameField.textProperty().addListener((observable, oldValue, newValue) ->
                search.findByEntityFields(newValue)
        );
        setData(search.getFilteredData());
    }

    private void setupSearchByProductId() {
        searchByProductIdField.textProperty().addListener((observable, oldValue, newValue) ->
                search.findByFieldName("productId", newValue)
        );
        setData(search.getFilteredData());
    }

    private void addOrderItem() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();;
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(selectedProduct);
        orderItem.setProductId(selectedProduct.getId());
        orderItem.setProductQuantity(Integer.parseInt(quantityField.getText()));
        orderItem.setOrder(NEW_ORDER);
        NEW_ORDER.getOrderItems().add(orderItem);
        fillOrderItemTable();
    }

    private void removeSelectedOrderItem() {
        OrderItem selectedItem = orderItemTable.getSelectionModel().getSelectedItem();
        NEW_ORDER.removeOrderItem(selectedItem);
        fillOrderItemTable();
    }

    private void fillProductTable() {
        productsTable.setItems(FXCollections.observableArrayList(PRODUCT_SERVICE.findAll()));
    }

    private void fillOrderItemTable() {
        orderItemTable.setItems(FXCollections.observableArrayList(NEW_ORDER.getOrderItems()));
    }

    private void setData(FilteredList<Product> filteredData) {
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productsTable.comparatorProperty());
        productsTable.setItems(sortedData);
    }

//    private void setData(FilteredList<Product> filteredData) {
//        SortedList<Product> sortedData = new SortedList<>(filteredData);
//        sortedData.comparatorProperty().bind(productsTable.comparatorProperty());
//        productsTable.setItems(sortedData);
//    }
}
