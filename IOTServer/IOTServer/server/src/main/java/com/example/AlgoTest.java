package com.example;
import java.util.ArrayList;
import java.util.Collections;

public class AlgoTest{
    public static ArrayList<String> findCarpark(ArrayList<String>Carparks, ArrayList<Double>Rates, ArrayList<Double>NumberofLots, int time) {
        ArrayList<String> carpark=new ArrayList<>();
        ArrayList<Double> cost = new ArrayList<>();
        ArrayList<Double> combined=new ArrayList<>();
        ArrayList<Double>costnegative=new ArrayList<>();
        for (int j=0;j<Carparks.size();j++){
            if(NumberofLots.get(j)==0){
                Carparks.remove(j);
                Rates.remove(j);
                NumberofLots.remove(j);
            }
        }
        for (int i = 0; i < Rates.size(); i++) {
            double carparkcost = Rates.get(i) * time;
            cost.add(carparkcost);
            carparkcost=-carparkcost;
            costnegative.add(carparkcost);
        }
        for(int k=0; k<Carparks.size();k++){
            double combi=7*NumberofLots.get(k)+3*costnegative.get(k);
            combined.add(combi);
        }
        int mincostCarparkIndex = cost.indexOf(Collections.min(cost));

        carpark.add(Carparks.get(mincostCarparkIndex));

        int maxlots=NumberofLots.indexOf(Collections.max(NumberofLots));
        carpark.add(Carparks.get(maxlots));

        int bestcombi=combined.indexOf(Collections.max(combined));
        carpark.add(Carparks.get(bestcombi));


        return carpark;//(min cost,max lots, combined)
    }

    public static void main(String[] args) {
        ArrayList<String>testcarpark=new ArrayList<>();
        testcarpark.add("SUTD");
        testcarpark.add("EXPO");
        testcarpark.add("CCP");
        testcarpark.add("CBP");
        ArrayList<Double>testrate=new ArrayList<>();
        testrate.add(2.00);
        testrate.add(4.00);
        testrate.add(2.50);
        testrate.add(3.00);
        ArrayList<Double>testlots= new ArrayList<>();
        testlots.add(100.0);
        testlots.add(100.0);
        testlots.add(100.0);
        testlots.add(100.0);
        int testtime=5;
        System.out.println(findCarpark(testcarpark,testrate,testlots,testtime));
    }
}