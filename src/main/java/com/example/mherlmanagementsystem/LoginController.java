package com.example.mherlmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController {

     Stage LoginStage;
    @FXML
    protected void Login(ActionEvent event){
        // This is the login method
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText("Login Successful");
        alert.setContentText("Welcome to MHERL Management System");
        alert.showAndWait();

    }

    public void setStage(Stage stage) {
        this.LoginStage = stage;
    }
}