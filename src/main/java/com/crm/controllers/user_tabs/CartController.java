package com.crm.controllers.user_tabs;

import java.math.BigDecimal;
import java.net.URL;
import java.security.Key;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import com.crm.controllers.LoginController;
import com.crm.entities.Order;
import com.crm.entities.OrderItem;
import com.crm.entities.User;
import com.crm.service.OrderService;
import com.crm.service.impl.OrderServiceImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

public class CartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<OrderItem, String> productMeasureCol;

    @FXML
    private Button createOrderBtn;

    @FXML
    private Button removeBtn;

    @FXML
    private TableView<OrderItem> orderItemTable;

    @FXML
    private TextField quantityField;

    @FXML
    private TableColumn<OrderItem, String> productQuantityCol;

    @FXML
    private TableColumn<OrderItem, String> productNameCol;

    @FXML
    private TableColumn<OrderItem, String> productPriceCol;

    @FXML
    private TableColumn<OrderItem, String> productPriceSumCol;

    @FXML
    private TextField sumField;

    private final OrderService ORDER_SERVICE = new OrderServiceImpl();
    private Order order = new Order();

    @FXML
    void initialize() {
        order.setOrderItems(UserCatalogTabController.CART);

        setupOrderItemsTableCellsValueFactory();

        fillOrderItemsTable();

        createOrderBtn.setOnAction(event -> saveOrder());
        removeBtn.setOnAction(event -> removeProduct());

        quantityField.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.ENTER ) {
                changeProductQuantity();
            }
        } );

    }

    private void setupOrderItemsTableCellsValueFactory() {
        productNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productMeasureCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getMeasure()));
        productPriceCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getPrice().toString()));
        productPriceSumCol.setCellValueFactory(new PropertyValueFactory<>("sum"));
    }

    private void fillOrderItemsTable() {
        List<OrderItem> orderItems = this.order.getOrderItems();
        ObservableList<OrderItem> orderItemsData = FXCollections.observableArrayList(orderItems);
        orderItemTable.setItems(orderItemsData);


        BigDecimal sum = (ORDER_SERVICE.getOrderItemsTotalSum(order));
        sumField.setText(sum.toString());
    }

    private void saveOrder() {
        User loggedInUser = LoginController.getLoggedInUser();
        order.setUser(loggedInUser);
        order.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        order.setStatus("PENDING");
        order.setSum(ORDER_SERVICE.getOrderItemsTotalSum(order));
        order.getOrderItems().forEach(orderItem -> orderItem.setOrder(order));
        ORDER_SERVICE.save(order);
    }


    private void removeProduct() {
        OrderItem selectedProduct = orderItemTable.getSelectionModel().getSelectedItem();
        order.getOrderItems().remove(selectedProduct);
        fillOrderItemsTable();
    }

    private void changeProductQuantity() {
        OrderItem selectedProduct = orderItemTable.getSelectionModel().getSelectedItem();
        int quantity = Integer.parseInt(quantityField.getText());
        selectedProduct.setProductQuantity(quantity);
        fillOrderItemsTable();
    }
}

