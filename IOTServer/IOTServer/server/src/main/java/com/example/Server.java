package com.example;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server{
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;

    //constructor
    public Server(){

    }

    //accept client request and return the lots
    public void returnLots() throws ClassNotFoundException, FileNotFoundException{
        try{
            server = new ServerSocket(1234,100);
            while(true){
                try{
                    connection = server.accept();
                    setupStreams();
                    optimise();
                }catch (EOFException e){
                    e.printStackTrace();
                }finally{
                    output.close();
                    input.close();
                    connection.close();
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //setup the stream between server and client
    private void setupStreams() throws IOException{
        input = new ObjectInputStream(connection.getInputStream());
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

    }



    //call the optimiser top optimise the parking lots
    private void optimise() throws IOException, ClassNotFoundException{
        double[] requestInfo = (double[]) input.readObject();

        //print check the input
        for (double a: requestInfo) {
            System.out.println(a);
        }

        double lat = requestInfo[0];
        double lang = requestInfo[1];
        double duration = requestInfo[2];

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

        ref.orderByChild("lat").startAt(lat-0.005).endAt(lat+0.005).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("test3");
                System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ArrayList<String> carpark;
        ArrayList<String> rates;
        ArrayList<String> lots;

        //String[] result = optimiseLots(requestInfo);
        //ArrayList<String> result = findCarpark(carpark,rates,lots,duration);

        //output.writeObject(result);
        //output.flush();

    }
}
