package com.example;

import java.io.IOException;

public class ClientTest {
    public static void main(String[] args)throws IOException{

        //input     : double[3] (lat, lang, duration in mins)
        //output    : String[9] (lot1name, lot1rates, lot1lots, lot2name, lot2rates, lot2lots, lot3name, lot3rates, lot3lots)

        double[] input1 = new double[]{100,200,20};
        double[] input2 = new double[]{90,90,90};
        double[] input3 = new double[]{40,38,100};

        Client bella = new Client("127.0.0.1");
        String[] a = bella.getLots(input3);
        for (String x:a) {
            System.out.println(x);
        }
    }
}