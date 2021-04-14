package com.crm.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AdminMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button orderCreateBtn;

    @FXML
    private TextField orderSearchField;

    @FXML
    private Button logoutBtn;

    @FXML
    private ListView<?> docsSelectedOrderList;

    @FXML
    private ListView<?> catalogList;

    @FXML
    private TextField searchField;

    @FXML
    private Button orderDeleteBtn;

    @FXML
    private ListView<?> orderList;

    @FXML
    private ListView<?> docsOrderList;

    @FXML
    private TextField docsOrderNumber;

    @FXML
    private Button docsSelectBtn;

    @FXML
    private Button docsClearBtn;

    @FXML
    private Button docsGenerateBtn;

    @FXML
    private Button bucketBtn;

    @FXML
    private ComboBox<?> orderCategoryComboBox;

    @FXML
    private Button docsRemoveBtn;

    @FXML
    private Button catalogSearchBtn;

    @FXML
    void initialize() {

    }
}

