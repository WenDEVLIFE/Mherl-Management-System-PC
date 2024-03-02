package com.example.mherlmanagementsystem;

import entities_and_functions.Products;
import entities_and_functions.Sales;
import firebase.FirebaseConfig;
import firebase.RetrieveFirebaseController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
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
import tables.ButtonCellDeleteSales;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class SalesController {

   private Stage salestage;

   private String username, userRole;

    @FXML
    private Text UsernameText;
    @FXML
    private Text RoleText;

    @FXML
    private ComboBox <String> monthlist;

    @FXML
    private ComboBox <String> yearliist;

    @FXML
    private TableView <Sales>SalesTable;

    ObservableList<Sales> SalesList = FXCollections.observableArrayList();

    public void setStage(Stage stageSales) {

        // Set the stage
        this.salestage = stageSales;
    }

    public void setUsernameInfo(String username, String userRole) {

        // Set the username and user role
        this.username = username;
        this.userRole = userRole;

        UsernameText.setText("Username:"+username);
        RoleText.setText("Role:"+userRole);


    }

    public void initialize() {
        // TODO
        FirebaseConfig.getInstance().initFirebase();

        ObservableList<String> month = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthlist.setItems(month);

        ObservableList<String> year = FXCollections.observableArrayList("2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030");
        yearliist.setItems(year);

        TableColumn<Sales, String> productName = new TableColumn<>("Product Name");
        productName.setCellValueFactory(data -> data.getValue().productNameProperty());
        productName.prefWidthProperty().bind(SalesTable.widthProperty().multiply(0.2)); // 20% width

        // Set the cell factory for the column
        productName.setCellFactory(tc -> {
            TableCell<Sales, String> cell = new TableCell<>() {
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

        TableColumn<Sales, Integer> productQuantity = new TableColumn<>("Product Quantity");
        productQuantity.setCellValueFactory(data -> data.getValue().productquantityProperty().asObject());
        productQuantity.prefWidthProperty().bind(SalesTable.widthProperty().multiply(0.2)); // 20% width
        // Set the cell factory for the column
        productQuantity.setCellFactory(tc -> {
            TableCell<Sales, Integer> cell = new TableCell<>() {
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

        TableColumn<Sales, Integer> totalPrice = new TableColumn<>("Total Price");
        totalPrice.setCellValueFactory(data -> data.getValue().totalpriceProperty().asObject());
        totalPrice.prefWidthProperty().bind(SalesTable.widthProperty().multiply(0.2)); // 20% width
        // Set the cell factory for the column
        totalPrice.setCellFactory(tc -> {
            TableCell<Sales, Integer> cell = new TableCell<>() {
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

        TableColumn<Sales, String> date = new TableColumn<>("Date");
        date.setCellValueFactory(data -> data.getValue().dateProperty());
        date.prefWidthProperty().bind(SalesTable.widthProperty().multiply(0.2)); // 20% width
        // Set the cell factory for the column
        date.setCellFactory(tc -> {
            TableCell<Sales, String> cell = new TableCell<>() {
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

        TableColumn <Sales, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new ButtonCellDeleteSales("Delete Sales", SalesTable, SalesList, username));
        actionCol.prefWidthProperty().bind(SalesTable.widthProperty().multiply(0.2)); // 20% width

        // Set the cell factory for the column
        actionCol.setCellFactory(tc -> {
            TableCell<Sales, Void> cell = new ButtonCellDeleteSales("Delete Sales", SalesTable, SalesList, username) {
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setMinHeight(60); // Set minimum height of the row
                }
            };
            return cell;
        });
        // Add the columns to the table
        SalesTable.getColumns().addAll(productName, productQuantity, totalPrice, date, actionCol);


        LoadSalesData();

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

        // Close the current stage
        salestage.close();


    }

    @FXML
    protected void ProoductActions(ActionEvent event) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("product.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
            Stage stage_product = new Stage();
            stage_product.getIcons().add(icon);
            stage_product.setTitle("Mherl Management System");
            stage_product.setScene(scene);
            stage_product.show();

            ProductController controller = fxmlLoader.getController();
            controller.setStage(stage_product);
            controller.setUsernameInfo(username, userRole);

            // Close the current stage
            salestage.close();
        } catch (IOException e) {
            e.printStackTrace();


        }


    }

    @FXML
    protected void SalesAction(ActionEvent event) {


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Sales");
        alert.setContentText("You are currently in the Sales");
        alert.showAndWait();


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
            salestage.close();

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
    protected void RefreshData(ActionEvent event){
        LoadSalesData();
    }

    public void LoadSalesData(){
        SalesList.clear();
        try {
            Platform.runLater(() -> {
                SalesList = RetrieveFirebaseController.getInstance().retrieveSales();
                SalesTable.setItems(SalesList);
                SalesTable.refresh();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
