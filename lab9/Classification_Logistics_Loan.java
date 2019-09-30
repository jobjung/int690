package com.company;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;

public class Classification_Logistics_Loan {
    String trainingFile;
    String testingFile;
    String predictFile;
    Classifier classifier;
    int classAttribute;

    public Classification_Logistics_Loan(String trainingFile, String testingFile, String predictFile, int classAttribute) {
        this.trainingFile = trainingFile;
        this.testingFile = testingFile;
        this.predictFile = predictFile;
        this.classAttribute = classAttribute;
    }

    public Classification_Logistics_Loan(Classifier classifier) {
        this.classifier = classifier;
    }

    public String getTrainingFile() {
        return trainingFile;
    }

    public void setTrainingFile(String trainingFile) {
        this.trainingFile = trainingFile;
    }

    public String getTestingFile() {
        return testingFile;
    }

    public void setTestingFile(String testingFile) {
        this.testingFile = testingFile;
    }

    public String getPredictFile() {
        return predictFile;
    }

    public void setPredictFile(String predictFile) {
        this.predictFile = predictFile;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    public int getClassAttribute() {
        return classAttribute;
    }

    public void setClassAttribute(int classAttribute) {
        this.classAttribute = classAttribute;
    }

    public Instances getDataSet(String filename) {
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(new File(filename));
            Instances dataset = loader.getDataSet();
            dataset.setClassIndex(this.classAttribute);
            return dataset;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void trainingAndTesting() {
        Instances trainingDataSet = getDataSet(this.trainingFile);
        Instances testingDataSet = getDataSet(this.testingFile);
        this.classifier = new Logistic();
        try {
            this.classifier.buildClassifier(trainingDataSet);
            Evaluation evaluation = new Evaluation(trainingDataSet);
            evaluation.evaluateModel(classifier, testingDataSet);
            System.out.println("Loan Model:");
            System.out.println(classifier);
            System.out.println("Loan Evaluation:");
            System.out.println(evaluation.toSummaryString(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void predictDataSet() {
        System.out.println("Predict From Data Set File");
        Instance predictDataSet;
        double answerValue = 0;
        Instances predictDataSets = getDataSet(this.predictFile);
        try {
            for (int i = 0; i < predictDataSets.numInstances(); i++) {
                predictDataSet = predictDataSets.instance(i);
                answerValue = this.classifier.classifyInstance(predictDataSet);
                printAttribute(predictDataSet);
                System.out.println((answerValue == 0 ? "You can loan" : "Sorry cannot loan"));
                System.out.println("-------");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printAttribute(Instance instance) {
        for (int i = 0; i < instance.numAttributes() - 1; i++) {
            System.out.print(instance.attribute(i).name() + ": " + instance.value(i)+",");
        }
        System.out.println();
    }


    public String predictOneInstance(String married, String dependent, String graduate,
                                     String selfEmployed, double appIncome, double coAppIncome, double loanAmount,
                                     double loanTerm, String propertyArea){

        try {
            String loanAnswer="";
            Instance predictDataSet = getDataSet(this.predictFile).instance(0);
            predictDataSet.setValue(0, married);
            predictDataSet.setValue(1, dependent);
            predictDataSet.setValue(2, graduate);
            predictDataSet.setValue(3, selfEmployed);
            predictDataSet.setValue(4, appIncome);
            predictDataSet.setValue(5, coAppIncome);
            predictDataSet.setValue(6, loanAmount);
            predictDataSet.setValue(7, loanTerm);
            predictDataSet.setValue(8, propertyArea);

            double value = classifier.classifyInstance(predictDataSet);
            printAttribute(predictDataSet);//debug
            loanAnswer = value==0?"Congratulation! You can loan":"Sorry, cannot loan";
            return loanAnswer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
