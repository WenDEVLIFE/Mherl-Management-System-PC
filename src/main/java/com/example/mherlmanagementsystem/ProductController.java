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
import tables.ButtonCellDeleteProducts;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class ProductController {

     private static ProductController instance;

     private static ProductController controller_products;

     private FXMLLoader fxmlLoader;

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
    private Tab EditProductTabNigga;

    @FXML
    private Tab BuyProductTabNigga;

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
    private TextField BuyProductName;

    @FXML
    private TextField BuyQuantity;


    @FXML
    private Text productnamelabel;

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

    public Boolean push_attempts1 = false;

    public Boolean push_attempts2 = false;

    public Boolean push_attempts3 = false;


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

    // Get the instance of the ProductController
    public static ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
    }


    // Set the FXMLLoader
    public void setFXMLLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    // Get the FXMLLoader
    public FXMLLoader getFxmlLoader() {
        return this.fxmlLoader;
    }

    // Get the instance of the ProductController
   public static ProductController getController_products() {
        if (controller_products == null) {
            controller_products = new ProductController();
        }
        return controller_products;
    }

    public void setController(ProductController controller) {
        this.controller_products = controller;
    }

    // Initialize the ProductController
    public void initialize() {
        System.out.println("ProductController initialized");

        // Set the textfields to be uneditable
        EditProductName.setEditable(false);
        EditQuantity.setEditable(false);
        EditPrice.setEditable(false);

        // Get the instance of the FirebaseController
        ProductionPane.getSelectionModel().select(ProductsTab);

        // Set the value factory for the spinner
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
        colBtn1.setCellFactory(param -> new ButtonCellDeleteProducts("Buy a product", ProductTable, ProductList, username));
        colBtn1.prefWidthProperty().bind(ProductTable.widthProperty().multiply(0.4)); // 40% width

        // Set the cell factory for the column
        colBtn1.setCellFactory(tc -> {
            TableCell<Products, Void> cell = new ButtonCellDeleteProducts("Buy a product", ProductTable, ProductList, username) {
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
        ProductTable.getColumns().addAll(productNameColumn, productPriceColumn, productQuantityColumn, colBtn, colBtn1, colBtn2);
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
            ClearAll();

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

        // Boolean to check if the user has attempted to edit the product name
        push_attempts1 = !push_attempts1;
        if (push_attempts1) {
            EditProductName.setEditable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Edit Product Name");
            alert.setContentText("You can now edit the product name");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Edit Product Name");
            alert.setContentText("You can no longer edit the product name");
            alert.showAndWait();
            EditProductName.setEditable(false);
        }

    }

    @FXML
    protected void edit_quantity(ActionEvent event){

        // Boolean to check if the user has attempted to edit the quantity
        push_attempts2 = !push_attempts2;
        if (push_attempts2) {
            EditQuantity.setEditable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Edit Product Quantity");
            alert.setContentText("You can now edit the product quantity");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Edit Product Quantity");
            alert.setContentText("You can no longer edit the product quantity");
            alert.showAndWait();
            EditQuantity.setEditable(false);
        }

    }

    @FXML
    protected void EditPrice(ActionEvent event){

        // Boolean to check if the user has attempted to edit the price
        push_attempts3 = !push_attempts3;


        if (push_attempts3) {
            EditPrice.setEditable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Edit Product Price");
            alert.setContentText("You can now edit the product price");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Edit Product Price");
            alert.setContentText("You can no longer edit the product price");
            alert.showAndWait();
            EditPrice.setEditable(false);
        }

    }

    @FXML
    protected void ConfirmEdit(ActionEvent event){

        String productName = EditProductName.getText();
        String price = EditPrice.getText();
        String OldProductName = productnamelabel.getText();
        int quantity = Integer.parseInt(EditQuantity.getText());

        if (EditProductName.isEditable() || EditQuantity.isEditable() || EditPrice.isEditable()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Edit Product");
            alert.setContentText("Are you sure you want to edit this product?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Edit product in database

                    Platform.runLater(() -> {
                        String [] info = {productName, price, OldProductName};
                        FirebaseController.getInstance().editProduct(info, quantity, username, productnamelabel);


                    });
                }
            });
        }

    }
    public void GoToEditProduct(Products selectedproducts) {
        if (EditProductTabNigga == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Please select a product to edit");
            alert.showAndWait();
        } else {
            ProductionPane.getSelectionModel().select(EditProductTabNigga);
            EditProductName.setText(selectedproducts.getProductName());
            EditQuantity.setText(String.valueOf(selectedproducts.getProductQuantity()));
            EditPrice.setText(selectedproducts.getProductPrice());
            productnamelabel.setText(selectedproducts.getProductName());
        }
    }

    public void GoToBuyProduct(Products selectedproducts) {

        if (BuyProductTabNigga == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Please select a product to buy");
            alert.showAndWait();
        } else {
            ProductionPane.getSelectionModel().select(BuyProductTabNigga);
            BuyProductName.setText(selectedproducts.getProductName());
            BuyProductName.setEditable(false);

        }
    }

    @FXML
    protected void BuyTheProduct(ActionEvent event){
        String productName = BuyProductName.getText();
        int quantity = Integer.parseInt(BuyQuantity.getText());
        if (productName.isEmpty() || quantity == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Please fill in all the fields");
            alert.showAndWait();
        } else {
            String[] details = {productName, username};
            Platform.runLater(() -> {
                FirebaseController.getInstance().buyProduct(details, quantity);
            });
        }

    }

    public void ClearAll() {
        Productname.clear();
        Price.clear();
        EditProductName.clear();
        EditQuantity.clear();
        EditPrice.clear();
        BuyProductName.clear();
        BuyQuantity.clear();

        ProductTable.getItems().clear();
        ProductList.clear();

    }




}
