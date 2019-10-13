package com.company;

public class Main {

    public static void main(String[] args) {
        KNN knn = new KNN("irisTraining.arff", "irisPredict.arff");
        knn.train();
        knn.predict();


        knn = new KNN("carTraining.arff", "carPredict.arff");
        knn.train();
        knn.predict();
    }
}
