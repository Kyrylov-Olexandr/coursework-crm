package com.crm.controllers.admin_tabs;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AdminCatalogTabController {

    @FXML private TextField searchByIdField;
    @FXML private TextField searchField;

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, String> productStockCol;
    @FXML private TableColumn<Product, String> productIdCol;
    @FXML private TableColumn<Product, BigDecimal> productPriceCol;
    @FXML private TableColumn<Product, String> productMeasureCol;

    @FXML private ImageView productImageHolder;

    @FXML private Button addProductBtn;
    @FXML private Button editProductBtn;
    @FXML private Button deleteProductBtn;
    @FXML private Button saveProductBtn;
    @FXML private Button refreshProductTableBtn;
    @FXML private Button showProductBtn;
    @FXML private Button downloadImageBtn;


    @FXML private TextField productNameField;
    @FXML private TextField productPriceField;
    @FXML private TextField productMeasureField;
    @FXML private TextArea productDescriptionField;

    @FXML private Label productStockLabel;

    @FXML private Label productIdLabel;

    @FXML private ResourceBundle resources;
    @FXML private URL location;

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

        showProductBtn.setOnAction(event -> showSelectedProduct());
        editProductBtn.setOnAction(event -> editSelectedProduct());
        saveProductBtn.setOnAction(event -> saveOrUpdateProduct());
        addProductBtn.setOnAction(event -> addProduct());
        deleteProductBtn.setOnAction(event -> deleteProduct());
        refreshProductTableBtn.setOnAction(event -> fillProductTable());
        downloadImageBtn.setOnAction(event -> downloadImage());
    }

    private void downloadImage() {
        Stage stage = (Stage) productTable.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Завантаження зображення");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter(" Images (*.jpg)", "*.jpg");

        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String uri = file.toURI().toString();
            Image image = new Image(uri);
            productImageHolder.setImage(image);

            this.selectedProduct.setImgFile(file);
            this.selectedProduct.setImgUrl(System.getProperty("user.dir"));
        }
    }

//    private String getImagePath(String absolutePath) {
//        int i = absolutePath.indexOf("/images/")
//    }


    private void deleteProduct() {
        Product product = productTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Підтвердження видалення");
        alert.setHeaderText("Видалення продукту: №" + product.getId() + " " + product.getName());
        alert.setContentText("Видилатити " + product.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            PRODUCT_SERVICE.delete(product);
        } else {
            alert.close();
        }
    }

    private void addProduct() {
        this.selectedProduct = new Product();
        clearProductFields();
        productIdLabel.setText("новий товар");
        if (!productNameField.isEditable()) {
            changeEditable();
        }
    }

    private void clearProductFields() {
        productNameField.clear();
        productPriceField.clear();
        productDescriptionField.clear();
        productMeasureField.clear();
        productStockLabel.setText("");
    }

    private void changeEditable() {
        boolean editable = productNameField.isEditable();
        productNameField.setEditable(!editable);
        productPriceField.setEditable(!editable);
        productMeasureField.setEditable(!editable);
        productDescriptionField.setEditable(!editable);
    }

    private void saveOrUpdateProduct() {
        Product product = this.selectedProduct;
        product.setName(productNameField.getText());
        product.setPrice(new BigDecimal(productPriceField.getText()));
        product.setDescription(productDescriptionField.getText());
        product.setMeasure(productMeasureField.getText());
        product.setQuantityInStock(0);
        PRODUCT_SERVICE.saveOrUpdate(selectedProduct);
    }


    private void editSelectedProduct() {
        changeEditable();
    }

    private void showSelectedProduct() {
        changeEditable();

        Product product = productTable.getSelectionModel().getSelectedItem();
        this.selectedProduct = product;

        productIdLabel.setText(String.valueOf(product.getId()));
        productNameField.setText(product.getName());
        productPriceField.setText(product.getPrice().toString());
        productMeasureField.setText(product.getMeasure());
        productStockLabel.setText(product.getQuantityInStock().toString());
        productDescriptionField.setText(product.getDescription());

        productImageHolder.setImage(new Image(selectedProduct.getImgUrl()));
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
