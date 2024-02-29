package com.example.mherlmanagementsystem;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import firebase.FirebaseConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class LoginController {

    Stage LoginStage;

    public void setStage(Stage stage) {
        this.LoginStage = stage;
    }

    public LoginController() {
    }

    public void initialize() {

    }

    @FXML
    protected void Login(ActionEvent event) {
        // This is the login method
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText("Login Successful");
        alert.setContentText("Welcome to MHERL Management System");
        alert.showAndWait();


        // Initialize Firebase
        FirebaseConfig firebaseConfig = new FirebaseConfig();
        firebaseConfig.initFirebase();

        // This is the constructor
        String message = "Hello, Firebase!, From JAVAFX";

        // Get a reference to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Get a reference to the "messages" node
        DatabaseReference messagesRef = database.getReference("messages");

        // Push a new message to the "messages" node
        messagesRef.push().setValueAsync(message);
    }





}