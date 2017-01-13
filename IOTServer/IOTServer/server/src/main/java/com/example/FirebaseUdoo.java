package com.example;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FirebaseUdoo {
    public static void main(String[] args) throws FileNotFoundException {

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(
                        new FileInputStream("./server/serviceAccountKey.json"))
                .setDatabaseUrl("https://parkingapp-b2b6f.firebaseio.com/")
                .build();

        // Initialize the default app
        FirebaseApp defaultApp = FirebaseApp.initializeApp(options);

        System.out.println(defaultApp.getName());

        // Retrieve services by passing the defaultApp variable...
        FirebaseDatabase db = FirebaseDatabase.getInstance(defaultApp);

        DatabaseReference ref = db.getReference("parkinginfo");

        String lotname = "The Manhattan";
        double lotnumber = 10;

        ref.child(lotname).child("lots").setValue(lotnumber);

        while(true){

        }

    }
}
