package com.example.mherlmanagementsystem;

import com.google.firebase.auth.FirebaseAuth;
import firebase.FirebaseConfig;
import firebase.FirebaseController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class DashboardController {


    private Stage stagedash;

    @FXML
    private Text UsernameText;
    @FXML
    private Text RoleText;

    @FXML
    private Text GreetingText;

    @FXML
    private Text AdminText;

    @FXML
    private Text ProductText;

    @FXML
    private Text UserText;

    @FXML
    private Text SalesText;

    @FXML
    private ImageView ImageViews;

    String username, userRole;

    public void setStage(Stage stagedash) {
        this.stagedash = stagedash;
    }

    public void setUsernameInfo(String username, String userRole) {

        // Set the username and user role
        this.username = username;
        this.userRole = userRole;

        UsernameText.setText("Username:"+username);
        RoleText.setText("Role:"+userRole);

        // Get the localtime
        LocalTime time = LocalTime.now();

        // Get the hour
        int hour = time.getHour();
        if (hour < 12) {
            GreetingText.setText("Good Morning, " + username + "!");
            ImageViews.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/sunrise.gif"))));
        } else if (hour < 16) {
            GreetingText.setText("Good Afternoon, " + username + "!");
            ImageViews.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/field.gif"))));
        } else {
            GreetingText.setText("Good Evening, " + username + "!");
            ImageViews.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/night.gif"))));
        }
    }

    public void initialize() {
        System.out.println("DashboardController initialized");
        FirebaseConfig.getInstance().initFirebase();


        // Get the instance of the FirebaseController
        Platform.runLater(() -> {

            // Get the instance of the FirebaseController
            FirebaseController controller = FirebaseController.getInstance();
            controller.setDashboardController(UserText, ProductText, SalesText, AdminText);
        });


    }
    @FXML
    protected void DashActions(ActionEvent event){

        // This is the dashboard action
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Dashboard");
        alert.setContentText("You are currently in the Dashboard");
        alert.showAndWait();

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

            stagedash.close();
        } catch (IOException e) {
            e.printStackTrace();


        }
    }

    @FXML
    protected void SalesAction(ActionEvent event){

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

            stagedash.close();
        } catch (IOException e) {
            e.printStackTrace();


        }

    }

    @FXML
    protected void AddUserAction(ActionEvent event){

    }

    @FXML
    protected void ReportAction(ActionEvent event){

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
            stagedash.close();

            FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin .class.getResource("hello-view.fxml"));
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
}
