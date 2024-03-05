package firebase;

import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import entities_and_functions.Products;
import entities_and_functions.Sales;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FirebaseController {

    // Firebase instance variable
    private static  FirebaseController instance;


    // Database reference
     DatabaseReference FireBaseDatabase;

     FirebaseDatabase database;




     // Singletton pattern
    public static FirebaseController getInstance() {
        if (instance == null) {
            instance = new FirebaseController();
        }
        return instance;
    }

  public FirebaseController () {
      FirebaseConfig.getInstance().initFirebase();



          // Our database reference
      FireBaseDatabase = FirebaseDatabase.getInstance().getReference();
      database = FirebaseDatabase.getInstance();


  }

  // This method is used to set the stats in dashboard
    public void setDashboardController(Text UserText, Text ProductText, Text SalesText, Text AdminText) {

        // get the user reference
        DatabaseReference usersRef = FireBaseDatabase.child("Users");
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
        DatabaseReference productsRef = FireBaseDatabase.child("Products");

        // This will count the product
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
        DatabaseReference salesRef = FireBaseDatabase.child("Sales");
        salesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    // i want to count the total price

                    // For data snapshot
                    long total_price = 0;

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        int price = snapshot.child("totalprice").getValue(Integer.class);
                        total_price += price;


                    }

                    // This will count the price
                    SalesText.setText(String.valueOf(total_price));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.

                System.out.println("Error: " + databaseError);
            }
        });

        // Admin reference
        DatabaseReference adminRef = FireBaseDatabase.child("Users");
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


    // This method is used to add a product
    public void addProduct(String[] details, int quantity, TextField Productname, TextField Price, Spinner <Integer> quantityspiner) {


        // This will add the product to the database
        DatabaseReference productsRef = FireBaseDatabase.child("Products");

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
                    product.put("quantity", quantity);
                    product.put("price", details[2]);

                    // then insert the value in hashmap
                    productsRef.child(productId).updateChildren(product, (databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            System.out.println("Data could not be saved " + databaseError.getMessage());

                        } else {
                            System.out.println("Data saved successfully.");

                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Dialog");
                                alert.setHeaderText("Information");
                                alert.setContentText("Product has been added successfully");
                                alert.showAndWait();

                                // This will clear the textfield
                                Productname.clear();
                                Price.clear();
                                quantityspiner.getValueFactory().setValue(1);
                            });


                        }
                    });

                    // This is for the success
                    LocalDate date = LocalDate.now();
                    LocalTime time = LocalTime.now();

                    // This is for the report
                    DatabaseReference reports = database.getReference("Reports");
                    String ReportId = UUID.randomUUID().toString();
                    String dateformat = date.toString();
                    String timeformat = time.toString();

                    Map<String, Object> report = new HashMap<>();
                    report.put("username", details[1]);
                    report.put("Activity", "Create Products");
                    report.put("Date", dateformat);
                    report.put("Time", timeformat);

                    // This is for the completion listener
                    reports.child(ReportId).updateChildren(report, (databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            System.out.println("Data could not be saved " + databaseError.getMessage());
                        } else {
                            System.out.println("Data saved successfully.");
                        }
                    });




                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Failed to add product");
                    alert.showAndWait();
                });

            }
        });
    }

    // This method is used to delete a product

    public void deleteProduct(Products selectedproducts, String username) {
        DatabaseReference productsRef = FireBaseDatabase.child("Products");

        String productname = selectedproducts.getProductName();

        // This is for the event listener
        productsRef.orderByChild("productname").equalTo(productname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue((databaseError, databaseReference) -> {
                            if (databaseError != null) {
                                System.out.println("Data could not be saved " + databaseError.getMessage());
                            } else {
                                System.out.println("Data saved successfully.");

                                Platform.runLater(() -> {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Information Dialog");
                                    alert.setHeaderText("Information");
                                    alert.setContentText("Product has been deleted successfully");
                                    alert.showAndWait();
                                });

                                // This is for the report
                                DatabaseReference reports = database.getReference("Reports");
                                String ReportId = UUID.randomUUID().toString();
                                LocalDate date = LocalDate.now();
                                LocalTime time = LocalTime.now();
                                String dateformat = date.toString();
                                String timeformat = time.toString();

                                // This is for the report
                                Map <String, Object> report = new HashMap<>();
                                report.put("username", username);
                                report.put("Activity", "Delete Product");
                                report.put("Date", dateformat);
                                report.put("Time", timeformat);
                                // This is for the completion listener
                                reports.child(ReportId).updateChildren(report, (databaseError1, databaseReference1) -> {
                                    if (databaseError1 != null) {
                                        System.out.println("Data could not be saved " + databaseError1.getMessage());
                                    } else {
                                        System.out.println("Data saved successfully.");
                                    }
                                });



                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Failed to delete product");
                    alert.showAndWait();
                });

            }
        });
    }

    // This method is used to delete sales
    public void deleteSales(Sales selectedproducts, String username) {

        String productname = selectedproducts.getProductname();
        DatabaseReference salesRef = FireBaseDatabase.child("Sales");

        // This is for the event listener
        salesRef.orderByChild("productname").equalTo(productname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue((databaseError, databaseReference) -> {
                            if (databaseError != null) {
                                System.out.println("Data could not be saved " + databaseError.getMessage());
                            } else {
                                System.out.println("Data saved successfully.");

                                Platform.runLater(() -> {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Information Dialog");
                                    alert.setHeaderText("Information");
                                    alert.setContentText("Sales has been deleted successfully");
                                    alert.showAndWait();
                                });

                                // This is for the report
                                DatabaseReference reports = database.getReference("Reports");
                                String ReportId = UUID.randomUUID().toString();
                                LocalDate date = LocalDate.now();
                                LocalTime time = LocalTime.now();
                                String dateformat = date.toString();
                                String timeformat = time.toString();

                                // This is for the report
                                Map <String, Object> report = new HashMap<>();
                                report.put("username", username);
                                report.put("Activity", "Delete Sales");
                                report.put("Date", dateformat);
                                report.put("Time", timeformat);

                                // This is for the completion listener
                                reports.child(ReportId).updateChildren(report, (databaseError1, databaseReference1) -> {
                                    if (databaseError1 != null) {
                                        System.out.println("Data could not be saved " + databaseError1.getMessage());
                                    } else {
                                        System.out.println("Data saved successfully.");
                                    }
                                });

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Failed to delete sales");
                    alert.showAndWait();
                });

            }
        });
    }


    // This method is for edit products
    public void editProduct(String [] info, int quantity, String username, Text productnamelabel) {
        DatabaseReference productsRef = FireBaseDatabase.child("Products");

        String oldproductName = info[2].toString();
        String newproductName = info[0].toString();
        String newprice = info[1].toString();
        int newquantity = quantity;

        // This is for the event listener
        productsRef.orderByChild("productname").equalTo(oldproductName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Map<String, Object> product = new HashMap<>();
                        product.put("productname", newproductName);
                        product.put("quantity", newquantity);
                        product.put("price", newprice);

                        snapshot.getRef().updateChildren(product, (databaseError, databaseReference) -> {
                            if (databaseError != null) {
                                System.out.println("Data could not be saved " + databaseError.getMessage());
                                Platform.runLater(() -> {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error Dialog");
                                    alert.setHeaderText("Error");
                                    alert.setContentText("Failed to update product");
                                    alert.showAndWait();

                                    productnamelabel.setText(newproductName);
                                });
                            } else {
                                System.out.println("Data saved successfully.");

                                Platform.runLater(() -> {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Information Dialog");
                                    alert.setHeaderText("Information");
                                    alert.setContentText("Product has been updated successfully");
                                    alert.showAndWait();

                                });

                                // This is for the report
                                DatabaseReference reports = database.getReference("Reports");
                                String ReportId = UUID.randomUUID().toString();
                                LocalDate date = LocalDate.now();
                                LocalTime time = LocalTime.now();
                                String dateformat = date.toString();
                                String timeformat = time.toString();

                                // This is for the report
                                Map<String, Object> report = new HashMap<>();
                                report.put("username", username);
                                report.put("Activity", "Edit Product");
                                report.put("Date", dateformat);
                                report.put("Time", timeformat);

                                // This is for the completion listener
                                reports.child(ReportId).updateChildren(report, (databaseError1, databaseReference1) -> {
                                    if (databaseError1 != null) {
                                        System.out.println("Data could not be saved " + databaseError1.getMessage());
                                    } else {
                                        System.out.println("Data saved successfully.");
                                    }
                                });



                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Failed to update product");
                    alert.showAndWait();
                });
            }
        });

    }

    public void buyProduct(String[] details, int quantity) {

        DatabaseReference productRef = FireBaseDatabase.child("Products");

        // This is for the event listener
        productRef.orderByChild("productname").equalTo(details[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                        // This is for the old quantity and new quantity
                        int oldquantity = snapshot.child("quantity").getValue(Integer.class);
                        int newquantity = oldquantity - quantity;
                        String price = snapshot.child("price").getValue(String.class);

                        if(newquantity >= 0){

                            // This is for the hashmap
                            Map<String, Object> product = new HashMap<>();
                            product.put("quantity", newquantity);

                            // This is for the completion listener
                            snapshot.getRef().updateChildren(product, (databaseError, databaseReference) -> {
                                if (databaseError != null) {
                                    System.out.println("Data could not be saved " + databaseError.getMessage());
                                } else {
                                    System.out.println("Data saved successfully.");

                                    Platform.runLater(() -> {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Information Dialog");
                                        alert.setHeaderText("Information");
                                        alert.setContentText("Product has been bought successfully");
                                        alert.showAndWait();
                                    });

                                    // This is for the report
                                    DatabaseReference reports = database.getReference("Reports");
                                    String ReportId = UUID.randomUUID().toString();
                                    LocalDate date = LocalDate.now();
                                    LocalTime time = LocalTime.now();
                                    String dateformat = date.toString();
                                    String timeformat = time.toString();

                                    // This is for the report
                                    Map<String, Object> report = new HashMap<>();
                                    report.put("username", details[1]);
                                    report.put("Activity", "Buy Product");
                                    report.put("Date", dateformat);
                                    report.put("Time", timeformat);

                                    // This is for the completion listener
                                    reports.child(ReportId).updateChildren(report, (databaseError1, databaseReference1) -> {
                                        if (databaseError1 != null) {
                                            System.out.println("Data could not be saved " + databaseError1.getMessage());
                                        } else {
                                            System.out.println("Data saved successfully.");
                                        }
                                    });

                                    int productprice = Integer.parseInt(price);
                                    int total_price = productprice * quantity;


                                    // This is for hashmap
                                    DatabaseReference sales = database.getReference("Sales");
                                    String SalesId = UUID.randomUUID().toString();
                                    Map<String, Object> sale = new HashMap<>();
                                    sale.put("productname", details[0]);
                                    sale.put("quantity", quantity);
                                    sale.put("date", dateformat);
                                    sale.put("totalprice", total_price);

                                    // insert the sales
                                    sales.child(SalesId).updateChildren(sale, (databaseError2, databaseReference2) -> {
                                        if (databaseError2 != null) {
                                            System.out.println("Data could not be saved " + databaseError2.getMessage());
                                        } else {
                                            System.out.println("Data saved successfully.");
                                        }
                                    });

                                }
                            });
                        } else{
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialog");
                                alert.setHeaderText("Error");
                                alert.setContentText("Product is out of stock");
                                alert.showAndWait();
                            });
                        }
                    }

                } else{
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Error");
                        alert.setContentText("Product does not exist");
                        alert.showAndWait();
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Failed to buy product");
                    alert.showAndWait();
                });

            }
        });

    }


    // This method will create a user
    public void createUser(String username, String password, String role) {

        DatabaseReference usersRef = FireBaseDatabase.child("Users");

        usersRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    // User already exists
                    // You can handle this case as you see fit, for example, show a message to the user
                    // This is for the failure
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Error");
                        alert.setContentText("User already exists");
                        alert.showAndWait();
                    });
                } else{
                    // User does not exist, create new user
                    // id
                    String userId = UUID.randomUUID().toString();

                    // This is for hashmap
                    Map<String, Object> user = new HashMap<>();
                    user.put("username", username);
                    user.put("password", password);
                    user.put("role", role);

                    // This is for the completion listener
                    usersRef.child(userId).updateChildren(user, (databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            System.out.println("Data could not be saved " + databaseError.getMessage());
                        } else {
                            System.out.println("Data saved successfully.");

                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Dialog");
                                alert.setHeaderText("Information");
                                alert.setContentText("User has been created successfully");
                                alert.showAndWait();
                            });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Failed to create user");
                    alert.showAndWait();
                });
            }
        });


    }


}
