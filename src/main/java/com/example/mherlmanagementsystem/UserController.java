package com.example.mherlmanagementsystem;

import entities_and_functions.User;
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
import tables.ButtonCellDeleteUser;

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

    @FXML
    private TextField EditUsername;

    @FXML
    private TextField EditPassword;

    @FXML
    private TextField EditRole;

    @FXML
    private TableView <User> UserTable;

    ObservableList <User> userList;

    String username, userRole;



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

        EditUsername.setEditable(false);
        EditPassword.setEditable(false);
        EditRole.setEditable(false);


        // Add items to the role combobox
        ObservableList <String > role = RoleBox.getItems();
        role.addAll("Select a role","Admin", "User");
        RoleBox.setItems(role);

        TableColumn <User, String> id = new TableColumn<>("Id");
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        id.prefWidthProperty().bind(UserTable.widthProperty().multiply(0.4)); // 40% width
        id.setCellFactory(tc -> {
            TableCell<User, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
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

        TableColumn <User, String> username1 = new TableColumn<>("Username");
        username1.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        username1.prefWidthProperty().bind(UserTable.widthProperty().multiply(0.4)); // 40% width
        username1.setCellFactory(tc -> {
            TableCell<User, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
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

        TableColumn <User, String> rolecolumn = new TableColumn<>("Role");
        rolecolumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        rolecolumn.prefWidthProperty().bind(UserTable.widthProperty().multiply(0.4)); // 40% width
        rolecolumn.setCellFactory(tc -> {
            TableCell<User, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
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

        TableColumn <User, Void> delete = new TableColumn<>("Delete User");
        delete.setCellFactory(param -> new ButtonCellDeleteUser("Delete User", UserTable, userList, username));
        delete.prefWidthProperty().bind(UserTable.widthProperty().multiply(0.4)); // 40% width

        // Set the cell factory for the column
        delete.setCellFactory(tc -> {
            TableCell<User, Void> cell = new ButtonCellDeleteUser("Delete User", UserTable, userList, username)   {
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setMinHeight(60); // Set minimum height of the row
                }
            };
            return cell;
        });

        TableColumn <User, Void> userinfo = new TableColumn<>("Show info");
        userinfo.setCellFactory(param -> new ButtonCellDeleteUser("Show info", UserTable, userList, username));
        userinfo.prefWidthProperty().bind(UserTable.widthProperty().multiply(0.4)); // 40% width

        // Set the cell factory for the column
        userinfo.setCellFactory(tc -> {
            TableCell<User, Void> cell = new ButtonCellDeleteUser("Show info", UserTable, userList, username) {
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setMinHeight(60); // Set minimum height of the row
                }
            };
            return cell;
        });

        UserTable.getColumns().addAll(id, username1, rolecolumn, delete, userinfo);






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

        // Go to create user tab
        UserPane.getSelectionModel().select(CreateUserTab);
    }

    @FXML
    protected void CreateUser(ActionEvent event){

        // Get the user input
        String username = RegisterUsername.getText();
        String password = RegisterPassword.getText();
        String confirmpassword = Confirmpassword.getText();
        String role = RoleBox.getValue();

        // Check if the fields are empty
        if (username.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || role.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please fill in all the fields.");
            alert.showAndWait();
        }  else {

            // Check if the password and confirm password match
          if (!password.equals(confirmpassword)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Password Mismatch");
                alert.setContentText("The passwords do not match.");
                alert.showAndWait();
            }
           else {

               // Check if the password length is less than 8
              if(password.length()<=8){
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Error");
                  alert.setHeaderText("Password Length");
                  alert.setContentText("The password should be more than 8 characters.");
                  alert.showAndWait();
              }

              else {
                  // Check if the password contains special characters
                  boolean hasSpecial = hasSpecialCharacters(password);
                  boolean hasUppercase = HasUpperCase(password);

                  // Check if has a special character
                  if (!hasSpecial) {
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                      alert.setTitle("Error");
                      alert.setHeaderText("Special Characters");
                      alert.setContentText("The password should contain special characters.");
                      alert.showAndWait();
                  } else {

                      // Check if the password has an uppercase character
                      if (!hasUppercase) {
                          Alert alert = new Alert(Alert.AlertType.ERROR);
                          alert.setTitle("Error");
                          alert.setHeaderText("Uppercase Characters");
                          alert.setContentText("The password should contain uppercase characters.");
                          alert.showAndWait();
                      } else {

                          // Create the user and send it to the firebase
                          FirebaseController.getInstance().createUser(username, password, role);
                      }
                  }
              }
          }
        }

    }

    // For boolean to check if the password has a special character
    public boolean hasSpecialCharacters(String password) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    // For boolean to check if the password has an uppercase character
    public boolean HasUpperCase(String password){
        Pattern uppercasePattern = Pattern.compile("[A-Z]");
        Matcher uppercaseMatcher = uppercasePattern.matcher(password);
        boolean hasUppercase = uppercaseMatcher.find();

        return  hasUppercase;
    }

    @FXML
    protected void SeeThePassword(ActionEvent event){
        if(seepassword1.isSelected()){
            RegisterPassword.setPromptText(RegisterPassword.getText());
            RegisterPassword.setText("");
            Confirmpassword.setPromptText(Confirmpassword.getText());
            Confirmpassword.setText("");
        }
        else{
            RegisterPassword.setText(RegisterPassword.getPromptText());
            Confirmpassword.setText(Confirmpassword.getPromptText());
        }
    }


    // This will go to Change User
    public void GoToChangeUser( String username, String password, String role) {

        UserPane.getSelectionModel().select(ChangeUsertab);

        EditUsername.setText(username);
        EditPassword.setText(password);
        EditRole.setText(role);



    }


    // For the clearing the fields
    public void ClearData(){
        RegisterPassword.setText("");
        RegisterUsername.setText("");
        Confirmpassword.setText("");
        RoleBox.setValue("Select a role");
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
