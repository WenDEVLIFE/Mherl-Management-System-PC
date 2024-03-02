package firebase;

import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import eu.hansolo.tilesfx.tools.Fire;
import javafx.scene.text.Text;

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

    public void addProduct(String productName, int price, int quantity) {

    }
}
