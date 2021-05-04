package com.crm.controllers.admin_tabs;

import com.crm.controllers.objects.Search;
import com.crm.entities.Product;
import com.crm.service.ProductService;
import com.crm.service.impl.ProductServiceImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class StockTabController extends AdminCatalogTabController{

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> productStockCol;
    @FXML private TableColumn<Product, String> productIdCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, String> productPriceCol;
    @FXML private TableColumn<Product, String> productMeasureCol;

    @FXML private TextField searchByIdField;
    @FXML private TextField searchField;

    @FXML private Button refreshBtn;
    @FXML private Button editBtn;

    private Search<Product> search;
    private Product selectedProduct;

    private final ProductService PRODUCT_SERVICE = new ProductServiceImpl();

    @FXML
    void initialize() {
        setupProductTableCellsValueFactory();

        fillProductTable();

        search = new Search<>(productTable);

        setupDynamicProductSearch();
        setupSearchByProductId();

        editBtn.setOnAction(event -> edit());
        refreshBtn.setOnAction(event -> fillProductTable());
    }

    private void edit() {
        this.selectedProduct = productTable.getSelectionModel().getSelectedItem();

        TextInputDialog dialog = new TextInputDialog(selectedProduct.getQuantityInStock().toString());
        dialog.setTitle(selectedProduct.getName());
        dialog.setHeaderText("Кількість на складі");
        dialog.setContentText("кількість:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            selectedProduct.setQuantityInStock(Integer.parseInt(result.get()));
            PRODUCT_SERVICE.update(selectedProduct);
        }
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

    private void setupSearchByProductId() {
        searchByIdField.textProperty().addListener((observable, oldValue, newValue) ->
                search.findByFieldName("productId", newValue)
        );
        setData(search.getFilteredData());
    }

    private void setupProductTableCellsValueFactory() {
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
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

