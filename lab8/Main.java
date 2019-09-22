package com.company;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        process("diabete_training.arff",
                "diabete_testing.arff",
                "diabete_predict.arff",8);

        process("weather_training.arff",
                "weather_testing.arff",
                "weather_predict.arff", 4);

        process("creditRisk_Clean_NoCreditHistory_training.arff",
                "creditRisk_Clean_NoCreditHistory_testing.arff",
                "creditRisk_Clean_NoCreditHistory_predict.arff", 9);

        process("creditRisk_Clean_HaveCreditHistory_training.arff",
                "creditRisk_Clean_HaveCreditHistory_testing.arff",
                "creditRisk_Clean_HaveCreditHistory_predict.arff", 10);


    }

    public static Instances getDataSet(String filename, int classIndex) {
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(new File(filename));
            Instances instances = loader.getDataSet();
            instances.setClassIndex(classIndex);
            return instances;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void process(String trainFilename,
                               String testFilename,
                               String predictFilename,
                               int classIndex) {
        Instances trainDataSet = getDataSet(trainFilename, classIndex);
        Instances testDataSet = getDataSet(testFilename, classIndex);
        Instances predictDataSet = getDataSet(predictFilename, classIndex);
        Classifier classifier = new Logistic();
        try {
            classifier.buildClassifier(trainDataSet);
            Evaluation evaluation = new Evaluation(trainDataSet);
            evaluation.evaluateModel(classifier, testDataSet);
            System.out.println("-------------------Logistics Regression-------------------");
            System.out.println(evaluation.toSummaryString());
            System.out.println("-------------------Logistics Regression Equation-------------------");
            System.out.println(classifier);
            for (int i = 0; i < predictDataSet.numInstances(); i++) {
                Instance predict = predictDataSet.instance(i);
                double value = classifier.classifyInstance(predict);
                double[] value2 = classifier.distributionForInstance(predict);
//                System.out.println("Predicted " + i + " : " + predict.attribute(2).name() + " " + predict.value(8) + " " + (value == 0 ? "You ard ok" : "You got diabete :)"));
                System.out.println("Predicted " + i + " : " + predict.attribute(classIndex).name() + " " + value);
//                for (int j = 0; j < value2.length; j++) {
//                    System.out.print(value2[j]+" ");
//                }
//                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}