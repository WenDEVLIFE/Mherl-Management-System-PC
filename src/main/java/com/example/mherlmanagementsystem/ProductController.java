package com.example.mherlmanagementsystem;

import firebase.FirebaseConfig;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProductController {

    private Stage productStage;

    @FXML
    private Text UsernameText;
    @FXML
    private Text RoleText;

    public void initialize() {
        System.out.println("ProductController initialized");
        FirebaseConfig.getInstance().initFirebase();
    }

}
