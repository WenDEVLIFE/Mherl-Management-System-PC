module com.example.mherlmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.auth.oauth2;
    requires firebase.admin;
    requires java.logging;

    opens com.example.mherlmanagementsystem to javafx.fxml;
    exports com.example.mherlmanagementsystem;
}
