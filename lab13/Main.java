package com.company;

import weka.core.Instances;

public class Main {

    public static void main(String[] args) {
        NN nn = new NN("bookTemplate.arff");
        Instances a = nn.getFromDB();
        nn.train();
//        for (int i = 0; i <a.numInstances() ; i++) {
//            System.out.println(a.instance(i));
//        }
    }
}
