package firebase;

import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import eu.hansolo.tilesfx.tools.Fire;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FirebaseController {

    private static  FirebaseController instance;

    private  DatabaseReference Database;
    public static FirebaseController getInstance() {
        if (instance == null) {
            instance = new FirebaseController();
        }
        return instance;
    }

  public FirebaseController () {
      FirebaseConfig.getInstance().initFirebase();



          // Our database reference
          Database = FirebaseDatabase.getInstance().getReference();


  }

    public void setDashboardController(Text UserText, Text ProductText, Text SalesText, Text AdminText) {

        // get the user reference
        DatabaseReference usersRef = Database.child("Users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    // count the user
                    long count = dataSnapshot.getChildrenCount();
                    UserText.setText(String.valueOf(count));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.

                System.out.println("Error: " + databaseError);
            }
        });

        // get the product reference
        DatabaseReference productsRef = Database.child("Products");
        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    // count the product
                    long count = dataSnapshot.getChildrenCount();
                    ProductText.setText(String.valueOf(count));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.

                System.out.println("Error: " + databaseError);
            }
        });

        //This will count the sales of the product
        DatabaseReference salesRef = Database.child("Sales");
        salesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    // i want to count the total price

                    // For data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        long total_price = 0;
                        int price = snapshot.child("totalprice").getValue(Integer.class);
                        total_price += price;

                        // This will count the price
                        SalesText.setText(String.valueOf(total_price));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.

                System.out.println("Error: " + databaseError);
            }
        });

        // Admin reference
        DatabaseReference adminRef = Database.child("Users");
        adminRef.orderByChild("role").equalTo("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    // count the admin
                    long count = dataSnapshot.getChildrenCount();

                    // This will set the value of the admin
                    AdminText.setText(String.valueOf(count));
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                // Handle possible errors.

                System.out.println("Error: " + databaseError);

            }
        });
    }

    public void addProduct(String[] details, int[] values, TextField Productname, TextField Price, Spinner <Integer> quantityspiner) {


        // This will add the product to the database
        DatabaseReference productsRef = Database.child("Products");

        productsRef.orderByChild("productname").equalTo(details[0]).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Product already exists
                    // You can handle this case as you see fit, for example, show a message to the user
                    // This is for the failure
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Product already exists");
                    alert.showAndWait();


                } else {

                    // Product does not exist, create new product
                    // id
                    String productId = UUID.randomUUID().toString();

                    // This is for hashmap
                    Map<String, Object> product = new HashMap<>();
                    product.put("productname", details[0]);
                    product.put("quantity", values[0]);
                    product.put("price", values[1]);

                    // then insert the value in hashmap
                    productsRef.child(productId).updateChildren(product, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                System.out.println("Data could not be saved " + databaseError.getMessage());

                            } else {
                                System.out.println("Data saved successfully.");

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Dialog");
                                alert.setHeaderText("Information");
                                alert.setContentText("Product added successfully");
                                alert.showAndWait();

                                // This will clear the textfield
                                Productname.clear();
                                Price.clear();
                                quantityspiner.getValueFactory().setValue(0);
                            }
                        }
                    });

                    // This is for the success
                    LocalDate date = LocalDate.now();
                    LocalTime time = LocalTime.now();

                    // This is for the report
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reports = database.getReference("Reports");
                    String ReportId = UUID.randomUUID().toString();
                    String dateformat = date.toString();
                    String timeformat = time.toString();

                    Map<String, Object> report = new HashMap<>();
                    report.put("username", details[1]);
                    report.put("Activity", "Create Products");
                    report.put("Date", dateformat);
                    report.put("Time", timeformat);
                    reports.child(ReportId).updateChildren(report, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                System.out.println("Data could not be saved " + databaseError.getMessage());
                            } else {
                                System.out.println("Data saved successfully.");
                            }
                        }
                    });


                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.

            }
        });
    }
}
