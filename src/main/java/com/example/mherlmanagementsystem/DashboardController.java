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
import javafx.scene.control.Button;
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


      Platform.runLater(() -> {

          try {


              stagedash.close();

              FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("product.fxml"));
              Scene scene = new Scene(fxmlLoader.load());
              Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
              stagedash.getIcons().add(icon);
              stagedash.setTitle("Mherl Management System");
              stagedash.setScene(scene);
              stagedash.show();

              ProductController controller = fxmlLoader.getController();
              controller.setStage(stagedash);
              controller.setFXMLLoader(fxmlLoader);
              controller.setController(controller);
              controller.setUsernameInfo(username, userRole);
              Clear();
          } catch (IOException e) {
              e.printStackTrace();


          }
      });
    }

    @FXML
    protected void SalesAction(ActionEvent event){

        Platform.runLater(() -> {
            try {

                stagedash.close();

                FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("sales.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
                stagedash.getIcons().add(icon);
                stagedash.setTitle("Mherl Management System");
                stagedash.setScene(scene);
                stagedash.show();

                SalesController controller = fxmlLoader.getController();
                controller.setStage(stagedash);

                Clear();
            } catch (IOException e) {
                e.printStackTrace();


            }
        });

    }

    @FXML
    protected void AddUserAction(ActionEvent event){
        Platform.runLater(  () ->{

            try {

                stagedash.close();

                FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin.class.getResource("user.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
                stagedash.getIcons().add(icon);
                stagedash.setTitle("Mherl Management System");
                stagedash.setScene(scene);
                stagedash.show();

                UserController controller = fxmlLoader.getController();
                controller.setStage(stagedash);
                controller.setController(controller);
                controller.setUsernameInfo(username, userRole);

                Clear();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

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
            stagedash.getIcons().add(icon);
            stagedash.setTitle("Mherl Management System");
            stagedash.setScene(scene);
            stagedash.show();

            LoginController controller = fxmlLoader.getController();
            controller.setStage(stagedash);
            Clear();

        } else {
            // User chose No or closed the dialog, do nothing
            System.out.println("Logout cancelled.");
        }


    }

    public void Clear(){
        AdminText.setText("");
        ProductText.setText("");
        UserText.setText("");
        SalesText.setText("");

        DashboardButton.setOnAction(null);
        AddProductButton.setOnAction(null);
        SalesButton.setOnAction(null);
        AddUserButton.setOnAction(null);
        ReportButton.setOnAction(null);
        LogoutButton.setOnAction(null);
        ImageViews.setImage(null);


    }
}
