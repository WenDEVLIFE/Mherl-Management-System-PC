package com.example.mherlmanagementsystem;

import firebase.FirebaseConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MherlLogin extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FirebaseConfig.getInstance().initFirebase();
        FXMLLoader fxmlLoader = new FXMLLoader(MherlLogin .class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/store-removebg-preview.png")));
        stage.getIcons().add(icon);
        stage.setTitle("Mherl Management System");
        stage.setScene(scene);
        stage.show();

        LoginController controller = fxmlLoader.getController();
        controller.setStage(stage);

    }

    public static void main(String[] args) {
        launch();
    }
}