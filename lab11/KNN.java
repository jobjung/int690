package com.company;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;

public class KNN {
    String trainingDataFile;
    String predictDataFile;
    int bestK;

    public KNN(String trainingDataFile, String predictDataFile) {
        this.trainingDataFile = trainingDataFile;
        this.predictDataFile = predictDataFile;
    }

    public Instances getDataSet(String flieName) {
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(new File(flieName));
            Instances dataSet = loader.getDataSet();
            dataSet.setClassIndex(dataSet.numAttributes()-1);
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void train() {
        Instances trainData = getDataSet(this.trainingDataFile);
        try {
            double percentComparison = 0;
            double currentPerntComparison = 0;
            this.bestK = 0;
            for (int i = 3; i < 20; i += 2) {
                Classifier ibk = new IBk(i);
                ibk.buildClassifier(trainData);
                Evaluation evaluation = new Evaluation(trainData);
                evaluation.evaluateModel(ibk, trainData);
                currentPerntComparison = evaluation.pctCorrect();
                if (currentPerntComparison > percentComparison) {
                    percentComparison = currentPerntComparison;
                    this.bestK = i;
                }

                System.out.println(ibk);
                System.out.println(evaluation.toMatrixString());
            }
            System.out.println(this.bestK + " " + percentComparison);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void predict() {
        Instances trainData = getDataSet(this.trainingDataFile);
        Instances predictData = getDataSet(this.predictDataFile);
        try {
            Classifier ibk = new IBk(this.bestK);
            System.out.println("Predict");
            ibk.buildClassifier(trainData);

            for (int i = 0; i < predictData.numInstances(); i++) {
                Instance data = predictData.instance(i);
                double answer = ibk.classifyInstance(data);
                System.out.println(data.toString() + " : " + data.attribute(data.numAttributes()-1).value((int) answer));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
