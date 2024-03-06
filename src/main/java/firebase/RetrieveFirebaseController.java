package firebase;

import com.google.firebase.database.*;
import entities_and_functions.Products;
import entities_and_functions.Report;
import entities_and_functions.Sales;
import entities_and_functions.User;
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

    private  final Map<String, User> userMap;

    private final Map<String ,Report> reportMap;


    public static RetrieveFirebaseController getInstance() {
        if (instance == null) {
            instance = new RetrieveFirebaseController();
        }
        return instance;
    }

    // Initialize
    public RetrieveFirebaseController() {
        FirebaseConfig.getInstance().initFirebase();
        database = FirebaseDatabase.getInstance();
        productsMap = new HashMap<>();
        salesMap = new HashMap<>();
        userMap = new HashMap<>();
        reportMap = new HashMap<>();
        PopulateData();

    }

    // Populate data
    public void PopulateData(){
        populateProducts();
        populateSales();
        populateUser();
        populateReports();

  }



    // return products

    public ObservableList<Products> retrieveProducts() {
        return FXCollections.observableArrayList(productsMap.values());
    }

    // Retrieve products
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
    // Return sales

    public ObservableList<Sales> retrieveSales() {
       return FXCollections.observableArrayList(salesMap.values());
    }

    // Retrieve sales
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

    // return the user
    public ObservableList<User> RetrieveUser() {
        return FXCollections.observableArrayList(userMap.values());
    }

    // Rertrieve the user
    private void populateUser() {
        myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot UserSnapshot : dataSnapshot.getChildren()) {
                    String id = UserSnapshot.child("id").getValue(String.class);
                    String username = UserSnapshot.child("username").getValue(String.class);
                    String role = UserSnapshot.child("role").getValue(String.class);

                    User user = new User(username, role);
                    userMap.put(username, user);
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

    public ObservableList<Report> getReports() {
        return FXCollections.observableArrayList(reportMap.values());
    }
    private void populateReports() {
        myRef = database.getReference("Reports");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ReportSnapshot : dataSnapshot.getChildren()) {
                    String reportId = ReportSnapshot.getKey(); // Assuming reportId is the unique identifier
                    String username = ReportSnapshot.child("username").getValue(String.class);
                    String report = ReportSnapshot.child("Activity").getValue(String.class);
                    String date = ReportSnapshot.child("Date").getValue(String.class);
                    String time = ReportSnapshot.child("Time").getValue(String.class);

                    Report report1 = new Report(username, report, date, time);
                    reportMap.put(reportId, report1); // Using reportId as the key
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Failed to retrieve data from the database");
                    alert.showAndWait();
                });
            }
        });
    }

}
