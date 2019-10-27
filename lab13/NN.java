package com.company;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class NN {
    String template_training_dataset_filename;

    public NN(String template_training_dataset_filename) {
        this.template_training_dataset_filename = template_training_dataset_filename;
    }

    public Instances getDataSet(String filename) {
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(new File(filename));
            Instances dataSet = loader.getDataSet();
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Instances getFromDB() {
        Instances dataSet = getDataSet(this.template_training_dataset_filename);
        dataSet.delete(0);
        Instance newInstance = new Instance(5);
        newInstance.setDataset(dataSet);

        String connectionURL = "jdbc:db2://10.4.53.14:50000/SAMPLE";
        Connection connection;
        java.sql.Statement statement;
        java.sql.ResultSet resultSet;

        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
            connection = DriverManager.getConnection(connectionURL, "stds61302", "ds690");
            statement = connection.createStatement();
            String query = "select LOCATION, AGE, b.ISBN, YEAROFPUBLICATION ,BOOKRATING\n" +
                    "from WARAPORN.USERS u,\n" +
                    "     WARAPORN.BOOKRATINGS br,\n" +
                    "     WARAPORN.BOOKS b\n" +
                    "where u.USERID = br.USERID\n" +
                    "  and br.ISBN = b.ISBN\n" +
                    "  and AGE is not null and BOOKRATING > 0 ";
            resultSet = statement.executeQuery(query);
            String[] address;
            while (resultSet.next()) {
                address = resultSet.getString(1).split(",");
                if (address.length > 2) {
                    newInstance.setValue(dataSet.attribute("country"), address[2].trim());
                    newInstance.setValue(dataSet.attribute("age"), resultSet.getInt(2));
                    if (resultSet.getString(3).endsWith("X")) {
                        String isbn = resultSet.getString(3).substring(0, resultSet.getString(3).length() - 1);
                        newInstance.setValue(dataSet.attribute("bookisbn"), Long.parseLong(isbn));
                    } else {
                        newInstance.setValue(dataSet.attribute("bookisbn"), Long.parseLong(resultSet.getString(3)));
                    }
                    newInstance.setValue(dataSet.attribute("yearpublication"), resultSet.getInt(4));
                    newInstance.setValue(dataSet.attribute("rating"), resultSet.getString(5));
                    dataSet.add(newInstance);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    public void train() {
        Instances trainingData = getFromDB();
        trainingData.setClassIndex(trainingData.numAttributes() - 1);
        MultilayerPerceptron mlp = new MultilayerPerceptron();
        mlp.setLearningRate(0.2);
        mlp.setMomentum(0.2);
        mlp.setHiddenLayers("13");
        mlp.setTrainingTime(5000);
        mlp.setValidationThreshold(90);
        try {
            mlp.buildClassifier(trainingData);
            Evaluation evaluation = new Evaluation(trainingData);
            evaluation.evaluateModel(mlp, trainingData);
            System.out.println(evaluation.toSummaryString());


            Classifier ibk = new IBk(1);
            double currentPercentCompare = 0;
            int bestK = 0;
            for (int i = 3; i < 30; i++) {
                ibk = new IBk(i);
                ibk.buildClassifier(trainingData);
                System.out.println("knn = " + i);
                System.out.println(ibk);
                evaluation = new Evaluation(trainingData);
                evaluation.evaluateModel(ibk, trainingData);
                if (evaluation.pctCorrect() > currentPercentCompare) {
                    currentPercentCompare = evaluation.pctCorrect();
                    bestK = i;
                }
                System.out.println(evaluation.toSummaryString());
                System.out.println(evaluation.toClassDetailsString());
                System.out.println(evaluation.toMatrixString());
            }
            System.out.println(bestK);

            ibk = new IBk(bestK);
            ibk.buildClassifier(trainingData);
            Instances predict = getDataSet("predictData.arff");
            predict.setClassIndex(trainingData.numAttributes() - 1);
            for (int i = 0; i < predict.numInstances(); i++) {
                double value = ibk.classifyInstance(predict.instance(i));
                System.out.println(predict.instance(i).toString() + " Rating->" + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
