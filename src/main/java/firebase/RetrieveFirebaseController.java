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

    private final Map<String , Products> productsMap;

    private  final Map<String, Sales> salesMap;


    public static RetrieveFirebaseController getInstance() {
        if (instance == null) {
            instance = new RetrieveFirebaseController();
        }
        return instance;
    }

    public RetrieveFirebaseController() {
        FirebaseConfig.getInstance().initFirebase();
         database = FirebaseDatabase.getInstance();
        productsMap = new HashMap<>();
        salesMap = new HashMap<>();
        PopulateData();

    }
  public void PopulateData(){
        populateProducts();
        populateSales();

  }

    public ObservableList<Products> retrieveProducts() {
        return FXCollections.observableArrayList(productsMap.values());
    }
    private void populateProducts() {
        myRef = database.getReference("Products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Productsnaphot : dataSnapshot.getChildren()) {
                    String productName = Productsnaphot.child("productname").getValue(String.class);
                    String productPrice = Productsnaphot.child("price").getValue(String.class);
                    int productQuantity = Productsnaphot.child("quantity").getValue(Integer.class);

                    Products products = new Products(productName, productPrice, productQuantity);
                    productsMap.put(productName, products);
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
    }




    public ObservableList<Sales> retrieveSales() {
       return FXCollections.observableArrayList(salesMap.values());
    }

    private void populateSales() {
        myRef = database.getReference("Sales");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot SalesSnapshot : dataSnapshot.getChildren()) {
                    String productName = SalesSnapshot.child("productname").getValue(String.class);
                    int productPrice = SalesSnapshot.child("totalprice").getValue(Integer.class);
                    int productQuantity = SalesSnapshot.child("quantity").getValue(Integer.class);
                    String date = SalesSnapshot.child("date").getValue(String.class);

                    Sales sales = new Sales(productName, productQuantity, productPrice, date);
                    salesMap.put(productName, sales);
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
    }



}
