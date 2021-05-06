package com.crm.controllers.user_tabs;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.crm.controllers.objects.Search;
import com.crm.entities.Order;
import com.crm.entities.OrderItem;
import com.crm.entities.Product;
import com.crm.service.OrderService;
import com.crm.service.ProductService;
import com.crm.service.impl.OrderServiceImpl;
import com.crm.service.impl.ProductServiceImpl;
import com.crm.utils.JavaFxUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserCatalogTabController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> productMeasureCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, String> productStockCol;
    @FXML private TableColumn<Product, String> productPriceCol;

    @FXML private TextField searchField;
    @FXML private TextField productMeasureField;
    @FXML private TextField productPriceField;
    @FXML private TextField productQuantityField;

    @FXML private TextArea productDescriptionField;

    @FXML private Label productNameLabel;

    @FXML private ImageView imageHolder;

    @FXML private Button addToCartBtn;
    @FXML private Button openCartBtn;
    @FXML private Button showProductBtn;

    private final OrderService ORDER_SERVICE = new OrderServiceImpl();
    private final ProductService PRODUCT_SERVICE = new ProductServiceImpl();

    private Product selectedProduct;
    private Search<Product> search;
    protected static List<OrderItem> CART = new ArrayList<>();

    @FXML
    void initialize() {
        setupProductTableCellsValueFactory();

        fillProductTable();

        search = new Search<>(productTable);

        setupDynamicProductSearch();

        showProductBtn.setOnAction(event -> showSelectedProduct());
        addToCartBtn.setOnAction(event -> addToCart(selectedProduct));
        openCartBtn.setOnAction(event -> JavaFxUtil.openUrl("/view/user_tabs/cart.fxml"));
    }

    private void addToCart(Product product) {
        OrderItem orderItem = new OrderItem();
        int quantity = Integer.parseInt(productQuantityField.getText());
        BigDecimal sum = BigDecimal.valueOf(quantity).multiply(product.getPrice());

        orderItem.setProduct(product);
        orderItem.setProductId(product.getId());
        orderItem.setProductQuantity(quantity);
        orderItem.setSum(sum);

        CART.add(orderItem);
    }

    private void showSelectedProduct() {
        Product product = productTable.getSelectionModel().getSelectedItem();

        this.selectedProduct = product;

        productNameLabel.setText(product.getName());
        productPriceField.setText(product.getPrice().toString());
        productMeasureField.setText(product.getMeasure());
        productQuantityField.setText(product.getQuantityInStock().toString());
        productDescriptionField.setText(product.getDescription());

        imageHolder.setImage(new Image(selectedProduct.getImgUrl()));
    }

    private void fillProductTable() {
        productTable.setItems(FXCollections.observableArrayList(PRODUCT_SERVICE.findAll()));
    }

    private void setupDynamicProductSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                search.findByEntityFields(newValue)
        );
        setData(search.getFilteredData());
    }

    private void setupProductTableCellsValueFactory() {
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productMeasureCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productStockCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getQuantityInStock().toString()));
    }

    private void setData(FilteredList<Product> filteredData) {
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedData);
    }
}
