package firebase;

import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import entities_and_functions.Products;
import entities_and_functions.Report;
import entities_and_functions.Sales;
import entities_and_functions.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        // Get the report Child reference
        myRef = database.getReference("Reports");

        // Add a value event listener to the report reference
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

    public void displayreportsstats(LineChart<String, Number> reportLineChart) {
        try {
            myRef = database.getReference("Reports");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Create an empty map to store the activity count for each hour
                        Map<String, Integer> activityCounts = new HashMap<>();

                        // Iterate over data
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String activityDate = snapshot.child("Date").getValue(String.class);
                            String activityTime = snapshot.child("Time").getValue(String.class);
                            String activity = snapshot.child("Activity").getValue(String.class);

                            // Ensure date, time, and activity are not null
                            if (activityDate != null && activityTime != null && activity != null) {
                                // Extract hour from time
                                if (activityTime.length() >= 5) {
                                    activityTime = activityTime.substring(0, 5);
                                }

                                // Concatenate date and time to match the format in your database
                                String dateTime = activityDate + "-" + activityTime;

                                // Increment count for the corresponding date-time combination
                                activityCounts.put(dateTime, activityCounts.getOrDefault(dateTime, 0) + 1);
                            }
                        }

                        // Create a new series
                        LineChart.Series<String, Number> series = new LineChart.Series<>();

                        // Add data to the series
                        for (Map.Entry<String, Integer> entry : activityCounts.entrySet()) {
                            series.getData().add(new LineChart.Data<>(entry.getKey(), entry.getValue()));
                        }

                        // Set a name for the series
                        series.setName("Activity Peaks");

                        // Add the series to the chart
                        reportLineChart.getData().add(series);

                        // Set chart labels
                        reportLineChart.setTitle("Activity Statistics");
                        reportLineChart.getXAxis().setLabel("Date-Time");
                        reportLineChart.getYAxis().setLabel("Number of Activities");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle error
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Failed to retrieve data from the database");
                        alert.showAndWait();
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error processing data.");
        }
    }




}
