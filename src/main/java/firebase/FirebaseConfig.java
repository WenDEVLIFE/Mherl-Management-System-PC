package firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirebaseConfig {

    private static FirebaseConfig instance;

    public static FirebaseConfig getInstance() {
        if (instance == null) {
            instance = new FirebaseConfig();
        }
        return instance;
    }

    public void initFirebase() {
        FileInputStream serviceAccount = null;
        try {
            String filePath = "src/main/resources/service_key.json";
            serviceAccount = new FileInputStream(filePath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://mherlmanagementsystem-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException ex) {
            Logger.getLogger(FirebaseConfig.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert serviceAccount != null;
                serviceAccount.close();
            } catch (IOException ex) {
                Logger.getLogger(FirebaseConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}