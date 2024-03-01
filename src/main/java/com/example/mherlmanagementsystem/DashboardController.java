package com.example.mherlmanagementsystem;

import com.google.firebase.auth.FirebaseAuth;
import firebase.FirebaseConfig;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
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

    String username, userRole;

    public void setStage(Stage stagedash) {
        this.stagedash = stagedash;
    }

    public void setUsernameInfo(String username, String userRole) {

        this.username = username;
        this.userRole = userRole;

        UsernameText.setText("Username:"+username);
        RoleText.setText("Role:"+userRole);
        GreetingText.setText("Welcome " + username + "!");
    }

    public void initialize() {
        System.out.println("DashboardController initialized");
        FirebaseConfig.getInstance().initFirebase();

    }
    @FXML
    protected void DashActions(ActionEvent event){

    }

    @FXML
    protected void ProoductActions(ActionEvent event){

    }

    @FXML
    protected void SalesAction(ActionEvent event){

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
