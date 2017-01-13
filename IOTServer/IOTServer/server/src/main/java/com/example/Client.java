package com.example;

import java.io.*;
import java.net.*;

public class Client{
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection;
    private String serverIP;

    //constructor
    public Client(String host){
        serverIP = host;
    }

    public String[] getLots(double[] in) throws IOException{
        String[] results = new String[9];
        try{
            connectToServer();
            setupStreams();
            results = requestLots(in);
        }catch (EOFException e){
            e.printStackTrace();
        }finally{
            output.close();
            input.close();
            connection.close();
            return results;
        }
    }

    private void connectToServer() throws IOException{
        connection = new Socket(InetAddress.getByName(serverIP),1234);
    }

    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
    }

    private String[] requestLots(double[] in) throws IOException, ClassNotFoundException{
        output.writeObject(in);
        output.flush();
        return (String[]) input.readObject();
    }
}
