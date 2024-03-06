package com.example.mherlmanagementsystem;

import entities_and_functions.Report;
import entities_and_functions.Sales;
import firebase.FirebaseConfig;
import firebase.RetrieveFirebaseController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tables.ButtonCellDeleteReport;
import tables.ButtonCellDeleteSales;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class ReportsController {

    private Stage stages;

    @FXML
    private Text UsernameText;
    @FXML
    private Text RoleText;

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
    private TabPane ReportPane;


    @FXML
    private Tab ReportTab;

    @FXML
    private Tab ReportStats;

    @FXML
    private TableView<Report> ReportTable;

    ObservableList <Report> reportList;


    String username,userRole;

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

    public void initialize(){


        // Initialize Firebase
        FirebaseConfig.getInstance().initFirebase();


        // This will go to report tab, and open it by default
        ReportPane.getSelectionModel().select(ReportTab);

        TableColumn <Report, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        usernameColumn.prefWidthProperty().bind(ReportTable.widthProperty().multiply(0.2)); // 20% width
        // Set the cell factory for the column
        usernameColumn.setCellFactory(tc -> {
            TableCell<Report, String> cell = new TableCell<>() {
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

        TableColumn <Report, String> AcivityColumn = new TableColumn<>("Activity");
        AcivityColumn.setCellValueFactory(cellData -> cellData.getValue().activityProperty());
        AcivityColumn.prefWidthProperty().bind(ReportTable.widthProperty().multiply(0.2)); // 20% width
        // Set the cell factory for the column
        AcivityColumn.setCellFactory(tc -> {
            TableCell<Report, String> cell = new TableCell<>() {
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

        TableColumn <Report, String> DateColumn = new TableColumn<>("Date");
        DateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        DateColumn.prefWidthProperty().bind(ReportTable.widthProperty().multiply(0.2)); // 20% width
        // Set the cell factory for the column
        DateColumn.setCellFactory(tc -> {
            TableCell<Report, String> cell = new TableCell<>() {
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

        TableColumn <Report, String> TimeColumn = new TableColumn<>("Time");
        TimeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        TimeColumn.prefWidthProperty().bind(ReportTable.widthProperty().multiply(0.2)); // 20% width
        // Set the cell factory for the column
        TimeColumn.setCellFactory(tc -> {
            TableCell<Report, String> cell = new TableCell<>() {
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

        TableColumn <Report, Void> ActionColumn = new TableColumn<>("Action");
        ActionColumn.prefWidthProperty().bind(ReportTable.widthProperty().multiply(0.2)); // 20% width
        // Set the cell factory for the column
        ActionColumn.setCellFactory(param -> new ButtonCellDeleteReport("Delete Report", ReportTable, reportList, username));
        ActionColumn.setCellFactory(tc -> {
            TableCell<Report, Void> cell = new ButtonCellDeleteReport("Delete Report", ReportTable, reportList, username) {
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setMinHeight(60); // Set minimum height of the row
                }
            };
            return cell;
        });

        // Add the columns to the table
        ReportTable.getColumns().addAll(usernameColumn, AcivityColumn, DateColumn, TimeColumn, ActionColumn);
        LoadReport();

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


            } catch (IOException e) {
                e.printStackTrace();


            }
        });


    }

    @FXML
    protected void SalesAction(ActionEvent event) {

        Platform.runLater(() -> {
            try {

                stages.close();

                FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("sales.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
                stages.getIcons().add(icon);
                stages.setTitle("Mherl Management System");
                stages.setScene(scene);
                stages.show();

                SalesController controller = fxmlLoader.getController();
                controller.setUsernameInfo(username, userRole);
                controller.setStage(stages);

            } catch (IOException e) {
                e.printStackTrace();


            }
        });



    }

    @FXML
    protected void AddUserAction(ActionEvent event){
        if (userRole.equals("Admin")) {
            Platform.runLater(  () ->{

                try {

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


                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Unauthorized Access");
            alert.setContentText("You are not authorized to access this page");
            alert.showAndWait();
        }

    }

    @FXML
    protected void ReportAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Reports");
        alert.setContentText("You are currently in the Reports");
        alert.show();


        // This will go to report tab, and open it by default
        ReportPane.getSelectionModel().select(ReportTab);
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
    protected void GoBack(ActionEvent event) {
        ReportPane.getSelectionModel().select(ReportTab);

    }

    @FXML
    protected void SeeReportStats(ActionEvent event){
        ReportPane.getSelectionModel().select(ReportStats);

    }


    public void LoadReport(){
        ReportTable.getItems().clear();
        try{
           Platform.runLater(() -> {
               RetrieveFirebaseController retrieveFirebaseController = new RetrieveFirebaseController();
               reportList = retrieveFirebaseController.getReports();
               ReportTable.setItems(reportList);
           });
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    private void ClearAll() {
        // Clear all the fields
        UsernameText.setText("");
        RoleText.setText("");

        // Clear the buttons
        DashboardButton.setOnAction(null);
        AddProductButton.setOnAction(null);
        SalesButton.setOnAction(null);
        AddUserButton.setOnAction(null);
        ReportButton.setOnAction(null);
        LogoutButton.setOnAction(null);

        // Clear the table
        ReportTable.getItems().clear();
        ReportTable.getColumns().clear();





    }


}
