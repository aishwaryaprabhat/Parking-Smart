package com.example;

import com.google.common.collect.Iterables;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class FirebaseText {

    //static final ArrayList<Carpark> nearbyCarparks = new ArrayList<>();
    static final ArrayList<String> carparks = new ArrayList<>();
    static final ArrayList<Double> lots = new ArrayList<>();
    static final ArrayList<Double> rate = new ArrayList<>();

    static final double lat = 1.34;
    static final double longs = 103.95;
    static int duration = 2;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(
                        new FileInputStream("./server/serviceAccountKey.json"))
                .setDatabaseUrl("https://parkingapp-b2b6f.firebaseio.com")
                .build();

        // Initialize the default app
        FirebaseApp defaultApp = FirebaseApp.initializeApp(options);

        System.out.println(defaultApp.getName());

        // Retrieve services by passing the defaultApp variable...

        FirebaseDatabase db = FirebaseDatabase.getInstance(defaultApp);

        DatabaseReference ref = db.getReference("parkinginfo");

        ref.orderByChild("longitude").startAt(longs-0.005).endAt(longs+0.005).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                returnData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        while(true){

        }
    }

    public static void returnData(DataSnapshot s){
        System.out.println("testData");
        Iterable<DataSnapshot> a = s.getChildren();
        Iterator<DataSnapshot> z = a.iterator();
        while(z.hasNext()){
            Carpark c = z.next().getValue(Carpark.class);
            System.out.println(c.getName());
            carparks.add(c.getName());
            lots.add(c.getLots());
            rate.add(c.getRate());

            System.out.println("carpark:"+carparks);
            System.out.println("lots:"+lots);
            System.out.println("rate:"+rate);

        }
        System.out.println(AlgoTest.findCarpark(carparks, rate, lots, duration));

    }

    static class Carpark{
        String name;
        double latitude;
        double longitude;
        double lots;
        double rate;

        public Carpark(){}

        public Carpark (double latitude, double longitude, double lots, double rate){
            this.latitude = latitude;
            this.longitude = longitude;
            this.lots = lots;
            this.rate = rate;
        }

        public void setLatitude(double lat){
            this.latitude=lat;
        }

        public void setLongitude(double longs){
            this.longitude=longs;
        }

        public void setLots(double lots){
            this.lots = lots;
        }

        public void setRate(double rate){
            this.rate = rate;
        }

        public void setName(String name){
            this.name = name;
        }

        public double getLatitude(){
            return latitude;
        }

        public double getLongitude(){
            return longitude;
        }

        public double getLots(){
            return lots;
        }

        public double getRate(){
            return rate;
        }

        public String getName(){
            return name;
        }
    }
}
