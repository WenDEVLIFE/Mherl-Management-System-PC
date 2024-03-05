package com.example.mherlmanagementsystem;

import eu.hansolo.tilesfx.tools.Fire;
import firebase.FirebaseController;
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

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController {

    private static UserController controller;

    private Stage stages;


    @FXML
    private Text UsernameText;
    @FXML
    private Text RoleText;

    private String username, userRole;

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
    private TabPane UserPane;

    @FXML
    private Tab UserTab;

    @FXML
    private Tab CreateUserTab;

    @FXML
    private Tab ChangeUsertab;

    @FXML
    private TextField RegisterUsername;

    @FXML
    private PasswordField RegisterPassword;

    @FXML
    private PasswordField Confirmpassword;

    @FXML
    private CheckBox seepassword1;

    @FXML
    private ComboBox <String>  RoleBox;

    public static UserController getController() {
        if (controller == null) {
            controller = new UserController();
        }
        return controller;
    }

    public void setController(UserController controller) {
        this.controller = controller;
    }
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

        UserPane.getSelectionModel().select(UserTab);


        // Add items to the role combobox
        ObservableList <String > role = RoleBox.getItems();
        role.addAll("Select a role","Admin", "User");
        RoleBox.setItems(role);
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
                controller.setStage(stages);
                ClearAll();
            } catch (IOException e) {
                e.printStackTrace();


            }
        });



    }

    @FXML
    protected void AddUserAction(ActionEvent event) {
        UserPane.getSelectionModel().select(UserTab);

    }

    @FXML
    protected void ReportAction(ActionEvent event) {

    }
    @FXML
    protected void GoBack(ActionEvent event) {
        UserPane.getSelectionModel().select(UserTab);
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
    protected void GoToCreateUser(ActionEvent event) {

        UserPane.getSelectionModel().select(CreateUserTab);
    }

    @FXML
    protected void CreateUser(ActionEvent event){

        String username = RegisterUsername.getText();
        String password = RegisterPassword.getText();
        String confirmpassword = Confirmpassword.getText();
        String role = RoleBox.getValue();

        if (username.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || role.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please fill in all the fields.");
            alert.showAndWait();
        }  else {
          if (!password.equals(confirmpassword)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Password Mismatch");
                alert.setContentText("The passwords do not match.");
                alert.showAndWait();
            }
           else {
              if(password.length()<=8){
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Error");
                  alert.setHeaderText("Password Length");
                  alert.setContentText("The password should be more than 8 characters.");
                  alert.showAndWait();
              }

              else {
                  boolean hasSpecial = hasSpecialCharacters(password);
                  boolean hasUppercase = HasUpperCase(password);

                  if (!hasSpecial) {
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                      alert.setTitle("Error");
                      alert.setHeaderText("Special Characters");
                      alert.setContentText("The password should contain special characters.");
                      alert.showAndWait();
                  } else {
                      if (!hasUppercase) {
                          Alert alert = new Alert(Alert.AlertType.ERROR);
                          alert.setTitle("Error");
                          alert.setHeaderText("Uppercase Characters");
                          alert.setContentText("The password should contain uppercase characters.");
                          alert.showAndWait();
                      } else {

                          FirebaseController.getInstance().createUser(username, password, role);
                      }
                  }
              }
          }
        }

    }

    public boolean hasSpecialCharacters(String password) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public boolean HasUpperCase(String password){
        Pattern uppercasePattern = Pattern.compile("[A-Z]");
        Matcher uppercaseMatcher = uppercasePattern.matcher(password);
        boolean hasUppercase = uppercaseMatcher.find();

        return  hasUppercase;
    }

    private void ClearAll() {
        // Clear all the fields
        UsernameText.setText("");
        RoleText.setText("");

        DashboardButton.setOnAction(null);
        AddProductButton.setOnAction(null);
        SalesButton.setOnAction(null);
        AddUserButton.setOnAction(null);
        ReportButton.setOnAction(null);
        LogoutButton.setOnAction(null);

    }

}
