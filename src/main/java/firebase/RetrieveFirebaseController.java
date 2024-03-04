package firebase;

import com.google.firebase.database.*;
import entities_and_functions.Products;
import entities_and_functions.Sales;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.Map;

public class RetrieveFirebaseController {

    private static RetrieveFirebaseController instance;

    private DatabaseReference myRef;

    private FirebaseDatabase database;


    public static RetrieveFirebaseController getInstance() {
        if (instance == null) {
            instance = new RetrieveFirebaseController();
        }
        return instance;
    }

    public RetrieveFirebaseController() {
        FirebaseConfig.getInstance().initFirebase();
         database = FirebaseDatabase.getInstance();


    }


    public ObservableList<Products> retrieveProducts() {
        ObservableList<Products> ProductList = FXCollections.observableArrayList();
        myRef = database.getReference("Products");

        // This is for the event listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("DataSnapshot: " + dataSnapshot.toString()); // Add this line
               ProductList.clear();
                // This will retrieve from the database
                for (DataSnapshot Productsnaphot : dataSnapshot.getChildren()) {
                    String productName = Productsnaphot.child("productname").getValue(String.class);
                    String productPrice = Productsnaphot.child("price").getValue(String.class);
                    int productQuantity = Productsnaphot.child("quantity").getValue(Integer.class);


                    ProductList.add(new Products(productName, productPrice, productQuantity));

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Failed to retrieve data from the database");
                alert.showAndWait();
            }
        });

          return ProductList;

    }


    public ObservableList<Sales> retrieveSales() {
        ObservableList<Sales> SalesList = FXCollections.observableArrayList();
        myRef = database.getReference("Sales");

        // This is for the event listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("DataSnapshot: " + dataSnapshot.toString()); // Add this line
                SalesList.clear();
                // This will retrieve from the database
                for (DataSnapshot SalesSnapshot : dataSnapshot.getChildren()) {
                    String productName = SalesSnapshot.child("productname").getValue(String.class);
                    int productPrice = SalesSnapshot.child("totalprice").getValue(Integer.class);
                    int productQuantity = SalesSnapshot.child("quantity").getValue(Integer.class);
                    String date = SalesSnapshot.child("date").getValue(String.class);

                    SalesList.add(new Sales(productName, productQuantity, productPrice, date));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Failed to retrieve data from the database");
                    alert.showAndWait();
                });
            }
        });
        return SalesList;
    }

    public void closeFirebase() {
        database.goOffline();
    }
}
