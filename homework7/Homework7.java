package com.company;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;

public class Homework7 {

    public static void main(String[] args) {
        process("fat_train.arff", "fat_test.arff", "fat_predict.arff");
    }

    public static Instances getDataSet(String filename) {
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(new File(filename));
            Instances instances = loader.getDataSet();
            instances.setClassIndex(1);
            return instances;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void process(String trainFilename,
                               String testFilename,
                               String predictFilename) {
        Instances trainDataSet = getDataSet(trainFilename);
        Instances testDataSet = getDataSet(testFilename);
        Instances predictDataSet = getDataSet(predictFilename);
        Classifier classifier = new LinearRegression();
        try {
            classifier.buildClassifier(trainDataSet);
            Evaluation evaluation = new Evaluation(trainDataSet);
            evaluation.evaluateModel(classifier, testDataSet);
            System.out.println("-------------------Linear Regression-------------------");
            System.out.println(evaluation.toSummaryString());
            System.out.println("-------------------Expression for the input data-------------------");
            System.out.println(classifier);
            for (int i = 0; i < predictDataSet.numInstances(); i++) {
                Instance pre = predictDataSet.instance(i);
                double value = classifier.classifyInstance(pre);
                System.out.println("Predicted " + pre.attribute(0).name() + " " + pre.value(0) + " : " + pre.attribute(1).name() + " " + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
