package com.example.mherlmanagementsystem;

import entities_and_functions.Sales;
import firebase.FirebaseConfig;
import firebase.RetrieveFirebaseController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tables.ButtonCellDeleteSales;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SalesController {

   private Stage stages;

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
    private Button DashboardButton;

    @FXML
    private Button AddProductButton;

    @FXML
    private Button SalesButton;

    @FXML
    private Button AddUserButton;

    @FXML
    private Button ReportButton;

    @FXML
    private Button LogoutButton;


    @FXML
    private Button RefreshButton;

    @FXML
    private Button SearchButton;

    @FXML
    private TableView <Sales>SalesTable;

    ObservableList<Sales> SalesList = FXCollections.observableArrayList();

    public void setStage(Stage stageSales) {

        // Set the stage
        this.stages = stageSales;
    }

    public void setUsernameInfo(String username, String userRole) {

        // Set the username and user role
        this.username = username;
        this.userRole = userRole;

        UsernameText.setText("Username:"+username);
        RoleText.setText("Role:"+userRole);


    }

    public void initialize() {
        Platform.runLater(() -> {
        // TODO

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


        });


    }


    @FXML
    protected void DashActions(ActionEvent event) throws IOException {

      Platform.runLater(() -> {
       try  {
           // Close the current stage
           stages.close();
           FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("dashboard.fxml"));
           Scene scene = new Scene(fxmlLoader.load());
           Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
           stages.getIcons().add(icon);
           stages.setTitle("Mherl Management System");
           stages.setScene(scene);
           stages.show();

           DashboardController controller = fxmlLoader.getController();
           controller.setStage(stages);
           controller.setUsernameInfo(username, userRole);


           ClearAll();
       } catch (Exception e) {
           e.printStackTrace();
       }
      });

    }

    @FXML
    protected void ProoductActions(ActionEvent event) {

   Platform.runLater(() -> {
       try {

           // Close the current stage
           stages.close();

           FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("product.fxml"));
           Scene scene = new Scene(fxmlLoader.load());
           Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
           stages.getIcons().add(icon);
           stages.setTitle("Mherl Management System");
           stages.setScene(scene);
           stages.show();

           ProductController controller = fxmlLoader.getController();
           controller.setStage(stages);
           controller.setFXMLLoader(fxmlLoader);
           controller.setController(controller);
           controller.setUsernameInfo(username, userRole);

           ClearAll();
       } catch (IOException e) {
           e.printStackTrace();


       }
   });


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

       if(userRole.equals("Admin")){
           Platform.runLater(() -> {
               try {

                   // Close the current stage
                   stages.close();

                   FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("user.fxml"));
                   Scene scene = new Scene(fxmlLoader.load());
                   Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
                   stages.getIcons().add(icon);
                   stages.setTitle("Mherl Management System");
                   stages.setScene(scene);
                   stages.show();

                   UserController controller = fxmlLoader.getController();
                   controller.setStage(stages);
                   controller.setController(controller);
                   controller.setUsernameInfo(username, userRole);

                   ClearAll();
               } catch (IOException e) {
                   e.printStackTrace();


               }
           });
       } else {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Information Dialog");
              alert.setHeaderText("Add User");
              alert.setContentText("You are not authorized to access this page");
              alert.showAndWait();

       }

    }

    @FXML
    protected void ReportAction(ActionEvent event) {
        if(userRole.equals("Admin")){
            Platform.runLater(() -> {
                try {

                    // Close the current stage
                    stages.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("report.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
                    stages.getIcons().add(icon);
                    stages.setTitle("Mherl Management System");
                    stages.setScene(scene);
                    stages.show();

                    UserController controller = fxmlLoader.getController();
                    controller.setStage(stages);
                    controller.setController(controller);
                    controller.setUsernameInfo(username, userRole);

                    ClearAll();
                } catch (IOException e) {
                    e.printStackTrace();


                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Add User");
            alert.setContentText("You are not authorized to access this page");
            alert.showAndWait();

        }
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

            Platform.runLater(() -> {
           try{

               stages.close();


               FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("hello-view.fxml"));
               Scene scene = new Scene(fxmlLoader.load());
               Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
               stages.getIcons().add(icon);
               stages.setTitle("Mherl Management System");
               stages.setScene(scene);
               stages.show();

               LoginController controller = fxmlLoader.getController();
               controller.setStage(stages);
               ClearAll();
           } catch (Exception e) {
               e.printStackTrace();}
            });


        } else {
            // User chose No or closed the dialog, do nothing
            System.out.println("Logout cancelled.");
        }


    }

    @FXML
    protected void RefreshData(ActionEvent event){
        LoadSalesData();
    }

    @FXML
    protected void PressSearch(ActionEvent event){

        String month = monthlist.getSelectionModel().getSelectedItem();
        String year = yearliist.getSelectionModel().getSelectedItem();

        if(month == null || year == null || month.equals("Select month") || year.equals("Select month")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Search");
            alert.setContentText("Please select the month and year to search");
            alert.showAndWait();
        }
        else {
            Platform.runLater(() -> {
                FilterValue(month, year);
            });
        }

    }

    private void FilterValue(String selectedMonth, String selectedYear) {

        // This method filters the sales data based on the selected month and year
        Platform.runLater(() -> {
            Map<String, String> monthNumberToName = new HashMap<>();
            monthNumberToName.put("01", "January");
            monthNumberToName.put("02", "February");
            monthNumberToName.put("03", "March");
            monthNumberToName.put("04", "April");
            monthNumberToName.put("05", "May");
            monthNumberToName.put("06", "June");
            monthNumberToName.put("07", "July");
            monthNumberToName.put("08", "August");
            monthNumberToName.put("09", "September");
            monthNumberToName.put("10", "October");
            monthNumberToName.put("11", "November");
            monthNumberToName.put("12", "December");

            FilteredList<Sales> filteredData = new FilteredList<>(SalesList, p -> true);
            filteredData.setPredicate(sales -> {
                String date = sales.getDate();
                String[] dateArray = date.split("-");
                String monthNumber = dateArray[1];
                String year = dateArray[0];
                String monthName = monthNumberToName.get(monthNumber);
                return monthName.equals(selectedMonth) && year.equals(selectedYear);
            });
            SortedList <Sales> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(SalesTable.comparatorProperty());
            SalesTable.setItems(sortedData);
            SalesTable.refresh();
        });
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

    public void ClearAll(){
        SalesList.clear();
        SalesTable.refresh();
        monthlist.getSelectionModel().clearSelection();
        yearliist.getSelectionModel().clearSelection();

        DashboardButton.setOnAction(null);
        AddProductButton.setOnAction(null);
        SalesButton.setOnAction(null);
        AddUserButton.setOnAction(null);
        ReportButton.setOnAction(null);
        LogoutButton.setOnAction(null);
        RefreshButton.setOnAction(null);
        SearchButton.setOnAction(null);

    }

}
