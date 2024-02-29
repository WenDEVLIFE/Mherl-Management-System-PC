package com.example.mherlmanagementsystem;

import com.google.firebase.database.*;
import firebase.FirebaseConfig;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    Stage LoginStage;

    @FXML
    private CustomPasswordField passwordfield;

    @FXML
    private TextField usernamefields;

    // Initialize Firebase only once


    public void setStage(Stage stage) {
        this.LoginStage = stage;
    }

    @FXML
    protected void LoginACTION(ActionEvent event) {
        // This is the login method
        String username = usernamefields.getText().trim();
        String password = passwordfield.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText("Login Failed");
            alert.setContentText("Please enter a valid username and password");
            alert.showAndWait();

        } else {
            // This is the login method
            LoginFireBase(username, password);

        }
    }

    public void initialize() {

        // This how you initialize Firebase
        FirebaseConfig.getInstance().initFirebase();
    }

    // This is the login method
    // Don't forget to add the FirebaseConfig class to the project and also use platform.runLater to update the UI

    protected void LoginFireBase(String username, String password) {
        // Get the DatabaseReference
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = database.child("Users");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String usernameFromDB = snapshot.child("username").getValue(String.class);
                    String passwordFromDB = snapshot.child("password").getValue(String.class);
                    if (usernameFromDB != null && usernameFromDB.equals(username) && passwordFromDB != null && passwordFromDB.equals(password)) {
                        // Username and password match, login successful
                        String userRole = snapshot.child("role").getValue(String.class);

                        // Update UI on JavaFX application thread
                        Platform.runLater(() -> {
                            try {
                                System.out.println("Login Successful");

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Login");
                                alert.setHeaderText("Login Successful");
                                alert.setContentText("Welcome " + username + "!");
                                alert.showAndWait();

                                LoginStage.close();

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
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        System.out.println("User Role: " + userRole);
                        return;
                    } else {
                        // Username and password do not match
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Login");
                            alert.setHeaderText("Login Failed");
                            alert.setContentText("Invalid username or password");
                            alert.showAndWait();
                        });
                    }
                } // End of for loop
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error handling
             Platform.runLater(() -> {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Login");
                 alert.setHeaderText("Login Failed");
                 alert.setContentText("An error occurred while logging in");
                 alert.showAndWait();
             });
            }
        });
    }



}
