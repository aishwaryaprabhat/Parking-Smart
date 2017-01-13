package com.example;

import java.io.FileNotFoundException;

public class RunServer {
    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException{
        Server server = new Server ();
        server.returnLots();
    }

    public static class Carpark {

        public String name;
        public double lat;
        public double lang;
        public double rate;
        public double lots;
        public double distance;

        public Carpark(String name, double lat, double lang, double rate) {

        }

        public double getDistance(double lt, double lg){
            return (Math.sqrt(((lat-lt)*(lat-lt))-((lang-lg)*(lang-lg))));
        }
    }

}
