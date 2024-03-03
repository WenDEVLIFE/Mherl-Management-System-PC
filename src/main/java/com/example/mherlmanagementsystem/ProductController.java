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

     private static ProductController instance;

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
    private Tab EditProductTab;

    @FXML
    private Tab BuyProductTab;

    @FXML
    private Spinner<Integer> quantityspiner;


    @FXML
    private TextField Productname;

    @FXML
    private TextField Price;

    @FXML
    private TextField EditProductName;

    @FXML
    private TextField EditQuantity;

    @FXML
    private TextField EditPrice;

    @FXML
    private TableView<Products> ProductTable;

    ObservableList<Products> ProductList = FXCollections.observableArrayList();

    @FXML
    TableColumn<Products, Void> colBtn;

    @FXML
    TableColumn<Products, Integer> productQuantityColumn;

    @FXML
    TableColumn<Products, String> productPriceColumn;

    @FXML
    TableColumn<Products, String> productNameColumn;


    String username, userRole;

    public void setStage(Stage stageProduct) {
        this.productStage = stageProduct;
    }

    public void setUsernameInfo(String username, String userRole) {

        // Set the username and role
        this.username = username;
        this.userRole = userRole;

        UsernameText.setText("Username:" + username);
        RoleText.setText("Role:" + userRole);
    }

    public static ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
    }

    public void initialize() {
        System.out.println("ProductController initialized");
        FirebaseConfig.getInstance().initFirebase();

        EditProductName.setEditable(false);
        EditQuantity.setEditable(false);
        EditPrice.setEditable(false);

        ProductionPane.getSelectionModel().select(ProductsTab);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
        quantityspiner.setValueFactory(valueFactory);

        // Set the cell value factory into the table
        productNameColumn = new TableColumn<>("Product Name");
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        productNameColumn.prefWidthProperty().bind(ProductTable.widthProperty().multiply(0.2)); // 20% width

        // Set the cell factory for the column
        productNameColumn.setCellFactory(tc -> {
            TableCell<Products, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setMinHeight(60); // Set minimum height of the row
                    }
                }
            };
            return cell;
        });

        productPriceColumn = new TableColumn<>("Product Price");
        productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty());
        productPriceColumn.prefWidthProperty().bind(ProductTable.widthProperty().multiply(0.2)); // 20% width

       // Set the cell factory for the column
        productPriceColumn.setCellFactory(tc -> {
            TableCell<Products, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setMinHeight(60); // Set minimum height of the row
                    }
                }
            };
            return cell;
        });

        productQuantityColumn = new TableColumn<>("Product Quantity");
        productQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().productQuantityProperty().asObject());
        productQuantityColumn.prefWidthProperty().bind(ProductTable.widthProperty().multiply(0.2)); // 20% width

        // Set the cell factory for the column
        productQuantityColumn.setCellFactory(tc -> {
            TableCell<Products, Integer> cell = new TableCell<>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(item));
                        setMinHeight(60); // Set minimum height of the row
                    }
                }
            };
            return cell;
        });

        colBtn = new TableColumn("Action");
        colBtn.setCellFactory(param -> new ButtonCellDeleteProducts("Delete Product", ProductTable, ProductList, username));
        colBtn.prefWidthProperty().bind(ProductTable.widthProperty().multiply(0.4)); // 40% width

        // Set the cell factory for the column
        colBtn.setCellFactory(tc -> {
            TableCell<Products, Void> cell = new ButtonCellDeleteProducts("Delete Product", ProductTable, ProductList, username) {
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setMinHeight(60); // Set minimum height of the row
                }
            };
            return cell;
        });

        TableColumn <Products, Void> colBtn1= new TableColumn("Action");
        colBtn1.setCellFactory(param -> new ButtonCellDeleteProducts("Buy", ProductTable, ProductList, username));
        colBtn1.prefWidthProperty().bind(ProductTable.widthProperty().multiply(0.4)); // 40% width

        // Set the cell factory for the column
        colBtn1.setCellFactory(tc -> {
            TableCell<Products, Void> cell = new ButtonCellDeleteProducts("Buy", ProductTable, ProductList, username) {
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setMinHeight(60); // Set minimum height of the row
                }
            };
            return cell;
        });


        TableColumn <Products, Void> colBtn2= new TableColumn("Action");
        colBtn2.setCellFactory(param -> new ButtonCellDeleteProducts("Product Info", ProductTable, ProductList, username));
        colBtn2.prefWidthProperty().bind(ProductTable.widthProperty().multiply(0.4)); // 40% width

        // Set the cell factory for the column
        colBtn2.setCellFactory(tc -> {
            TableCell<Products, Void> cell = new ButtonCellDeleteProducts("Product Info", ProductTable, ProductList, username) {
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setMinHeight(60); // Set minimum height of the row
                }
            };
            return cell;
        });

        // Add the columns to the table
        ProductTable.getColumns().addAll(productNameColumn, productPriceColumn, productQuantityColumn, colBtn);
        ProductTable.setMinHeight(800); // Set minimum height
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

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("sales.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
            Stage stage_sales = new Stage();
            stage_sales.getIcons().add(icon);
            stage_sales.setTitle("Mherl Management System");
            stage_sales.setScene(scene);
            stage_sales.show();

            SalesController controller = fxmlLoader.getController();
            controller.setStage(stage_sales);
            controller.setUsernameInfo(username, userRole);

            productStage.close();
        } catch (IOException e) {
            e.printStackTrace();


        }

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

    @FXML
    protected void edit_productname(ActionEvent event){

    }

    @FXML
    protected void edit_quantity(ActionEvent event){

    }

    @FXML
    protected void EditPrice(ActionEvent event){

    }

    @FXML
    protected void ConfirmEdit(ActionEvent event){
        if (EditProductName.isEditable() || EditQuantity.isEditable() || EditPrice.isEditable()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Edit Product");
            alert.setContentText("Are you sure you want to edit this product?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Edit product in database
                    Platform.runLater(() -> {

                    });
                }
            });
        }

    }
    public void GoToEditProduct() {
        ProductionPane.getSelectionModel().select(EditProductTab);
    }

    public void GoToBuyProduct() {
        ProductionPane.getSelectionModel().select(BuyProductTab);
    }


}
