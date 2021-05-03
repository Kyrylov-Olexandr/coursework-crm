package com.crm.controllers.admin_tabs;

import com.crm.controllers.objects.Search;
import com.crm.entities.Order;
import com.crm.entities.OrderItem;
import com.crm.service.OrderItemService;
import com.crm.service.OrderService;
import com.crm.service.UserService;
import com.crm.service.impl.OrderItemServiceImpl;
import com.crm.service.impl.OrderServiceImpl;
import com.crm.service.impl.UserServiceImpl;
import com.crm.utils.JavaFxUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrdersTabController {
    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, String> firstNameCol;
    @FXML private TableColumn<Order, String> lastNameCol;
    @FXML private TableColumn<Order, String> orderStatusCol;
    @FXML private TableColumn<Order, String> companyCol;
    @FXML private TableColumn<Order, String> emailCol;
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

    @FXML private Button updateOrderBtn;
    @FXML private Button addOrderBtn;
    @FXML private Button addOrderItemBtn;
    @FXML private Button deleteOrderBtn;
    @FXML private Button showClientInfoBtn;
    @FXML private Button deleteOrderItemBtn;
    @FXML private Button updateOrderItemBtn;
    @FXML private Button showProductsBtn;
    @FXML private Button refreshOrdersBtn;
    @FXML private Button refreshOrderItemsBtn;
    @FXML private Button saveOrderItemsBtn;

    @FXML private TextField searchByIdField;
    @FXML private TextField searchField;
    @FXML private TextField searchByUserIdField;

    @FXML private ComboBox<String> statusComboBox;

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    private final OrderItemService ORDER_ITEM_SERVICE = new OrderItemServiceImpl();
    private final OrderService ORDER_SERVICE = new OrderServiceImpl();
    private Search<Order> search;

    @FXML
    void initialize() {
        setupOrdersTableCellsValueFactory();
        setupOrderItemsTableCellsValueFactory();

        fillOrderTable();

        search = new Search<>(ordersTable);

        showProductsBtn.setOnAction(event -> fillOrderItemsTable());

        setupDynamicOrderSearch();
        setupSearchByOrderId();
        setupStatusComboBox();

        refreshOrdersBtn.setOnAction(event -> fillOrderTable());
        deleteOrderBtn.setOnAction(event -> deleteSelectedOrder());
        updateOrderItemBtn.setOnAction(event -> setupEditOrderItemDialog());
        updateOrderBtn.setOnAction(event -> setupEditOrderStatusDialog());
        deleteOrderItemBtn.setOnAction(event -> deleteSelectedOrderItem());
        addOrderBtn.setOnAction(event -> JavaFxUtil.openUrl("/view/admin_tabs/dialogs/addOrderDialog.fxml"));

    }



    private void deleteSelectedOrderItem() {
        OrderItem selectedItem = orderItemTable.getSelectionModel().getSelectedItem();
        ORDER_ITEM_SERVICE.delete(selectedItem);
    }

    private void setupEditOrderStatusDialog() {
        Order selectedOrder = getSelectedOrder();

        List<String> statuses = new ArrayList<>();
        statuses.add("COMPLETED");
        statuses.add("CANCELED");
        statuses.add("PENDING");
        statuses.add("CONFIRMED");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(selectedOrder.getStatus(), statuses);
        dialog.setTitle("Замовлення №" + selectedOrder.getId());
        dialog.setHeaderText("Редагування стану замовлення");
        dialog.setContentText("Стан:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String selectedStatus = dialog.getSelectedItem();
            selectedOrder.setStatus(selectedStatus);
            ORDER_SERVICE.update(selectedOrder);
        }
    }

    private void setupEditOrderItemDialog() {
        OrderItem selectedItem = orderItemTable.getSelectionModel().getSelectedItem();

        TextInputDialog dialog = new TextInputDialog(String.valueOf(selectedItem.getProductQuantity()));
        dialog.setTitle(selectedItem.getProduct().getName());
        dialog.setHeaderText("Редагування кількості");
        dialog.setContentText("Кількість:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int returnedQuantity = Integer.parseInt(dialog.getEditor().getText());
            selectedItem.setProductQuantity(returnedQuantity);
            ORDER_ITEM_SERVICE.update(selectedItem);
        }
    }

    private void fillOrderItemsTable() {
        List<OrderItem> orderItems = getSelectedOrder().getOrderItems();
        ObservableList<OrderItem> orderItemsData = FXCollections.observableArrayList(orderItems);
        orderItemTable.setItems(orderItemsData);
    }

    private Order getSelectedOrder() {
        return ordersTable.getSelectionModel().getSelectedItem();
    }

    private void deleteSelectedOrder() {
        Order order = ordersTable.getSelectionModel().getSelectedItem();
        ORDER_SERVICE.delete(order);
    }


    private void fillOrderTable() {
        ObservableList<Order> ordersData = FXCollections.observableArrayList(ORDER_SERVICE.findAll());
        ordersTable.setItems(ordersData);
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

    private void setupOrderItemsTableCellsValueFactory() {
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productPriceCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getPrice().toString()));
    }

    private void setupDynamicOrderSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                search.findByEntityFields(newValue)
        );
        setData(search.getFilteredData());
    }

    private void setupSearchByOrderId() {
        searchByIdField.textProperty().addListener((observable, oldValue, newValue) ->
                search.findByFieldName("orderId", newValue)
        );
        setData(search.getFilteredData());
    }

    private void setupStatusComboBox() {

        statusComboBox.getItems().addAll(
                "Всі",
                "Виконано",
                "В очікувані",
                "Відхилено",
                "Підтверджено"
        );

        statusComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
            switch (newItem) {
                case "Всі": {
                    search.findByEntityFields("");
                    break;
                }
                case "Виконано": {
                    search.findByFieldName("status", "COMPLETED");
                    setData(search.getFilteredData());
                    break;
                }
                case "Відхилено": {
                    search.findByFieldName("status", "CANCELED");
                    setData(search.getFilteredData());
                    break;
                }
                case "Підтверджено": {
                    search.findByFieldName("status", "CONFIRMED");
                    setData(search.getFilteredData());
                    break;
                }
                case "В очікувані": {
                    search.findByFieldName("status", "PENDING");
                    setData(search.getFilteredData());
                    break;
                }
            }
        });
    }

    private void setData(FilteredList<Order> filteredData) {
        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(ordersTable.comparatorProperty());
        ordersTable.setItems(sortedData);
    }
}
