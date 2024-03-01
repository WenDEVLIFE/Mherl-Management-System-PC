package com.example.mherlmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
}
