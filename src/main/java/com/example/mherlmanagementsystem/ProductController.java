package com.example.mherlmanagementsystem;

import entities_and_functions.Products;
import firebase.FirebaseConfig;
import firebase.FirebaseController;
import firebase.RetrieveFirebaseController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import tables.ButtonCellDeleteProducts;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class ProductController {


    private Stage productStage;

    @FXML
    private Text UsernameText;
    @FXML
    private Text RoleText;


    @FXML
    private TabPane ProductionPane;

    @FXML
    private Tab ProductsTab;

    @FXML
    private Tab CreateProductsTab;

    @FXML
    private Spinner<Integer> quantityspiner;


    @FXML
    private TextField Productname;

    @FXML
    private TextField Price;

    @FXML
    private TableView<Products> ProductTable;

    ObservableList<Products> ProductList = FXCollections.observableArrayList();


    String username, userRole;

    public void setStage(Stage stageProduct) {
        this.productStage = stageProduct;
    }

    public void setUsernameInfo(String username, String userRole) {

        this.username = username;
        this.userRole = userRole;

        UsernameText.setText("Username:" + username);
        RoleText.setText("Role:" + userRole);
    }


    public void initialize() {
        System.out.println("ProductController initialized");
        FirebaseConfig.getInstance().initFirebase();

        ProductionPane.getSelectionModel().select(ProductsTab);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantityspiner.setValueFactory(valueFactory);

        // Set the cell value factory into the table
        TableColumn<Products, String> productNameColumn = new TableColumn<>("Product Name");
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        productNameColumn.setMinWidth(200);

        TableColumn<Products, String> productPriceColumn = new TableColumn<>("Product Price");
        productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty());
        productPriceColumn.setMinWidth(200);

        TableColumn<Products, Integer> productQuantityColumn = new TableColumn<>("Product Quantity");
        productQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().productQuantityProperty().asObject());
        productPriceColumn.setMinWidth(200);


        TableColumn<Products, Void> colBtn = new TableColumn("Action");
        colBtn.setCellFactory(param -> new ButtonCellDeleteProducts("Delete Product", ProductTable, ProductList));
        colBtn.setMinWidth(100);

        ProductTable.getColumns().addAll(productNameColumn, productPriceColumn, productQuantityColumn, colBtn);

        LoadProducts();
    }

    @FXML
    protected void DashActions(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
        Stage stagedash = new Stage();
        stagedash.getIcons().add(icon);
        stagedash.setTitle("Mherl Management System");
        stagedash.setScene(scene);
        stagedash.show();

        DashboardController controller = fxmlLoader.getController();
        controller.setStage(stagedash);
        controller.setUsernameInfo(username, userRole);

        productStage.close();


    }

    @FXML
    protected void ProoductActions(ActionEvent event) {


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Product");
        alert.setContentText("You are currently in the Product");
        alert.showAndWait();


        ProductionPane.getSelectionModel().select(ProductsTab);
    }

    @FXML
    protected void SalesAction(ActionEvent event) {

    }

    @FXML
    protected void AddUserAction(ActionEvent event) {

    }

    @FXML
    protected void ReportAction(ActionEvent event) {

    }

    @FXML
    protected void LogoutAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Press Yes to logout, or No to cancel.");

        // Add Yes and No buttons to the alert
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Show the alert and wait for user response
        Optional<ButtonType> result = alert.showAndWait();

        // Handle user's choice
        if (result.isPresent() && result.get() == buttonTypeYes) {
            // User chose Yes, perform logout
            System.out.println("Logging out...");
            productStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
            Stage stage = new Stage();
            stage.getIcons().add(icon);
            stage.setTitle("Mherl Management System");
            stage.setScene(scene);
            stage.show();

            LoginController controller = fxmlLoader.getController();
            controller.setStage(stage);


        } else {
            // User chose No or closed the dialog, do nothing
            System.out.println("Logout cancelled.");
        }


    }


    @FXML
    private void GoToAddProducts(ActionEvent event) {
        ProductionPane.getSelectionModel().select(CreateProductsTab);
    }

    @FXML
    protected void CreateProducts(ActionEvent event) {

        String productName = Productname.getText();
        String price = Price.getText();
        int quantity = quantityspiner.getValue();

        if (productName.isEmpty() || price.isEmpty()  || quantity == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Please fill in all the fields");
            alert.showAndWait();
        } else {
            String[] details = {productName, username, price};
            Platform.runLater(() -> {
                FirebaseController.getInstance().addProduct(details, quantity, Productname, Price, quantityspiner);
            });
        }

    }

    @FXML
    protected void GoBack(ActionEvent event) {
        ProductionPane.getSelectionModel().select(ProductsTab);


    }

    public void LoadProducts() {
        ProductTable.getItems().clear();
        try {

            Platform.runLater(() -> {
                ProductList = RetrieveFirebaseController.getInstance().retrieveProducts();
                ProductTable.setItems(ProductList);
                ProductTable.refresh();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
