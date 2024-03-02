package firebase;

import com.google.firebase.database.*;
import entities_and_functions.Products;
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




}
