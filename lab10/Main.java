package com.company;

public class Main {

    public static void main(String[] args) {
        KMeanClustering xyClustering = new KMeanClustering("training_location.arff", "xyTesting.arff", "xyPredict.arff");
        xyClustering.trainAndTestSimpleKMean(15);
        xyClustering.predictInstances();
    }
}
