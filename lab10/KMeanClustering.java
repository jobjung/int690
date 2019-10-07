package com.company;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;


public class KMeanClustering {
    String training_filename;
    String testing_filename;
    String predict_filename;
    SimpleKMeans clusteringModel;

    public KMeanClustering(String training_filename, String testing_filename, String predict_filename) {
        this.training_filename = training_filename;
        this.testing_filename = testing_filename;
        this.predict_filename = predict_filename;
    }

    public Instances getDataSet(String filename) {
        try {
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(filename));
            Instances dataSet = loader.getDataSet();
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void trainAndTestSimpleKMean(int numberOfClusters) {
        try {
            System.out.println("Training");
            Instances trainingDataSet = getDataSet(training_filename);
            clusteringModel = new SimpleKMeans();
            clusteringModel.setNumClusters(numberOfClusters);
            clusteringModel.buildClusterer(trainingDataSet);
            System.out.println(clusteringModel);
            Instances centroids = clusteringModel.getClusterCentroids();
            for (int i = 0; i < clusteringModel.getNumClusters(); i++) {
                System.out.print("Centroid " + i + ": " + centroids.instance(i));
                System.out.println(". Centroid size:" + clusteringModel.getClusterSizes()[i]);
            }
            System.out.println("Testing with Class of clustering");
            Instances testingDataSet = getDataSet(testing_filename);
            testingDataSet.setClassIndex(testingDataSet.numAttributes() - 1);
            ClusterEvaluation evaluation = new ClusterEvaluation();
            evaluation.setClusterer(clusteringModel);
            evaluation.evaluateClusterer(testingDataSet);
            System.out.println(evaluation.clusterResultsToString());

//            System.out.println("Cross Validation ");
//            EM expectMaximizedModel = new EM();
//            expectMaximizedModel.setMaxIterations(15);
//            expectMaximizedModel.buildClusterer(trainingDataSet);
//            System.out.println(expectMaximizedModel);
//            double logLikehood = ClusterEvaluation.crossValidateModel(expectMaximizedModel, trainingDataSet, 15, new Random(1));
//            System.out.println("Logarithm of likelyhood is"+logLikehood);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void predictInstances() {
        System.out.println("Clustering Prediction");
        Instances predictDataSet = getDataSet(predict_filename);
        double clusterTh;
        Instance predictData;
        for (int i = 0; i < predictDataSet.numInstances(); i++) {

            predictData = predictDataSet.instance(i);
            try {
                clusterTh = clusteringModel.clusterInstance(predictData);
                System.out.println(predictData.toString() + " is in the cluster number " + clusterTh);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}