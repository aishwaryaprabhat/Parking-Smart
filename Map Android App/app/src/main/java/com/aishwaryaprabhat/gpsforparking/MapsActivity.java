package com.aishwaryaprabhat.gpsforparking;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.ResultReceiver;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.identity.intents.Address;
import android.location.Address;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    protected Location mLastLocation;
    private ResultReceiver mResultReceiver;
    private GoogleMap mMap;
    double lat;
    double longit;
    Double parking_lot_1_lat;
    Double parking_lot_2_lat;
    Double parking_lot_3_lat;
    Double parking_lot_1_long;
    Double parking_lot_2_long;
    Double parking_lot_3_long;
    String parking_lot_1_rate;
    String parking_lot_2_rate;
    String parking_lot_3_rate;
    String parking_lot_1_lots;
    String parking_lot_2_lots;
    String parking_lot_3_lots;


    private static final int portnumber = 52471;
    private static final String hostname = "10.13.33.145";
    //10.13.33.145
    private static final String debugstring = "debugString";
    private Socket socket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_maps);


        //Client-server stuff


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button search = (Button) findViewById(R.id.searchbutton);

        System.out.println("created");


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //push_request_to_client()
                System.out.println("clicked");
                ArrayList<String> parking_lot_names = new ArrayList<String>();
                parking_lot_names.add("Simei Block 256");
                parking_lot_names.add("Simei Block 244");
                parking_lot_names.add("My Manhattan");

                geolocate(view, parking_lot_names);
//                onMapReady(mMap);
            }
        });
    }

    public void geolocate(View v, final ArrayList<String> parking_lot_names) {

        System.out.println("geolocate");

        final EditText searchlocation = (EditText) findViewById(R.id.locationsearched);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(searchlocation.getWindowToken(), 0);
        String location = searchlocation.getText().toString();
        Geocoder gc = new Geocoder(this);

        List<Address> list_of_location = null;
        try {
            list_of_location = gc.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address add = list_of_location.get(0);
        String name = add.getFeatureName();
        final double lat_destination = add.getLatitude();
        final double longit_destination = add.getLongitude();

        LatLng destination = new LatLng(lat_destination, longit_destination);
        mMap.addMarker(new MarkerOptions().position(destination).title("Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));


        Firebase myurl = new Firebase("https://parkingapp-b2b6f.firebaseio.com/parkinginfo");
        myurl.child("Destination").child("name").setValue(name);
        myurl.child("Destination").child("lat").setValue(lat_destination);
        myurl.child("Destination").child("long").setValue(longit_destination);


        myurl.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                showMarker(dataSnapshot, parking_lot_names);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                System.out.println("cancelled");

            }
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australx   ia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng destination = new LatLng(1.341873, 103.963067);
        mMap.addMarker(new MarkerOptions().position(destination).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 18));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    public void showMarker(DataSnapshot dataSnapshot, ArrayList<String>parking_lot_names ){

                    parking_lot_1_lat = dataSnapshot.child(parking_lot_names.get(0)).child("latitude").getValue(Double.class);
                    parking_lot_2_lat = dataSnapshot.child(parking_lot_names.get(1)).child("latitude").getValue(Double.class);
                    parking_lot_3_lat = dataSnapshot.child(parking_lot_names.get(2)).child("latitude").getValue(Double.class);

                    parking_lot_1_long = dataSnapshot.child(parking_lot_names.get(0)).child("longitude").getValue(Double.class);
                    parking_lot_2_long =dataSnapshot.child(parking_lot_names.get(1)).child("longitude").getValue(Double.class);
                    parking_lot_3_long =dataSnapshot.child(parking_lot_names.get(2)).child("longitude").getValue(Double.class);

                    LatLng lot1 = new LatLng(parking_lot_1_lat,parking_lot_1_long);
                    LatLng lot2 = new LatLng(parking_lot_2_lat,parking_lot_2_long);
                    LatLng lot3 = new LatLng(parking_lot_3_lat,parking_lot_3_long);

                    parking_lot_1_lots = dataSnapshot.child(parking_lot_names.get(0)).child("lots").getValue(String.class);
                    parking_lot_2_lots = dataSnapshot.child(parking_lot_names.get(1)).child("lots").getValue(String.class);
                    parking_lot_3_lots = dataSnapshot.child(parking_lot_names.get(2)).child("lots").getValue(String.class);

                    parking_lot_1_rate = dataSnapshot.child(parking_lot_names.get(0)).child("rate").getValue(String.class);
                    parking_lot_2_rate = dataSnapshot.child(parking_lot_names.get(1)).child("rate").getValue(String.class);
                    parking_lot_3_rate = dataSnapshot.child(parking_lot_names.get(2)).child("rate").getValue(String.class);

                    mMap.addMarker(new MarkerOptions().position(lot1).title(parking_lot_1_lots+" price:6/hr"));
                    mMap.addMarker(new MarkerOptions().position(lot2).title("available lots:"+parking_lot_2_lots+" price:4/hr"));
                    mMap.addMarker(new MarkerOptions().position(lot3).title("available lots:"+parking_lot_3_lots+" price:3-/hr"));
//
//        Simei Block 256");
//        parking_lot_names.add("Simei Block 244");
//        parking_lot_names.add("My Manhattan");



    }

    public void talktoserver(final double lat_destination1, final double longit_destination1){
        new Thread() {

            //(debugstring,"Attempting to connect to server");



            {
                try {
                    socket = new Socket(hostname, portnumber);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //send coordinates
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bw.write(Double.toString(lat_destination1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bw.write(Double.toString(longit_destination1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Receive info from server
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Message from client" + br);

            }


        }.start();


    }

}
