package firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirebaseConfig {

    private static FirebaseConfig instance;


    //  This is the singleton pattern
    public static FirebaseConfig getInstance() {
        if (instance == null) {
            instance = new FirebaseConfig();
        }
        return instance;
    }

    public void initFirebase() {

        // This will check if the firebase instance is null
        if (FirebaseApp.getApps().isEmpty()) {
            FileInputStream serviceAccount = null;
            try {

                 // This is the path to the service key file
                String filePath = "src/main/resources/service_key.json";
                serviceAccount = new FileInputStream(filePath);
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://mherlmanagementsystem-default-rtdb.asia-southeast1.firebasedatabase.app")
                        .build();

                // Initialize the default app
                FirebaseApp.initializeApp(options);

                FirebaseApp defaultApp = FirebaseApp.getInstance();

                System.out.println(defaultApp.getName());  // "[DEFAULT]"

                // Retrieve services
                FirebaseAuth defaultAuth = FirebaseAuth.getInstance(defaultApp);
                FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance(defaultApp);
            } catch (IOException ex) {
                Logger.getLogger(FirebaseConfig.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {

                    // If  services not equal to null
                    if (serviceAccount != null) {

                        // Close the service account
                        serviceAccount.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FirebaseConfig.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Firebase already initialized.");
        }
    }

}