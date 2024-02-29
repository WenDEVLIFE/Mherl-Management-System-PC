package com.example.mherlmanagementsystem;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;

public class LoginController {
    FirebaseOptions options;
    Stage LoginStage;
    public void setStage(Stage stage) {
        this.LoginStage = stage;
    }

    public LoginController() throws IOException {

        FileInputStream serviceAccount = new FileInputStream("servicekey.json");
        options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://mherlmanagementsystem-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .build();

        FirebaseApp.initializeApp(options);


    }
    @FXML
    protected void Login(ActionEvent event){
        // This is the login method
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText("Login Successful");
        alert.setContentText("Welcome to MHERL Management System");
        alert.showAndWait();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
        // This is the constructor
        String message = "Hello, Firebase!, From JAVAFX";

        // Get a reference to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance(firebaseApp);

        // Get a reference to the "messages" node
        DatabaseReference messagesRef = database.getReference("messages");

        // Push a new message to the "messages" node
        messagesRef.push().setValueAsync(message);

    }

}