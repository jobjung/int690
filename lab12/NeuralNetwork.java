package com.company;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NeuralNetwork {
    String trainingFile;
    String predictFile;
    String modelFile;

    public NeuralNetwork(String trainingFile, String predictFile, String modelFile) {
        this.trainingFile = trainingFile;
        this.predictFile = predictFile;
        this.modelFile = modelFile;
    }

    public Instances getDataset(String filename) {
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(new File(filename));
            Instances dataSet = loader.getDataSet();
            dataSet.setClassIndex(dataSet.numAttributes() - 1);
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Instances getDatasetFromDB() {
        String connectionURL = "jdbc:db2://10.4.53.14:50000/SAMPLE";
        Connection connection;
        java.sql.Statement statement;
        java.sql.ResultSet resultSet;

        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
            connection = DriverManager.getConnection(connectionURL, "stds61302", "ds690");
            statement = connection.createStatement();
            String st = "select e.FIRSTNME, e.LASTNAME, p.PROJNAME, ep.ACTNO, e.SALARY\n" +
                    "from DB2INST1.EMPLOYEE e,\n" +
                    "     DB2INST1.PROJECT p,\n" +
                    "     DB2INST1.EMPPROJACT ep\n" +
                    "where p.PROJNO = ep.PROJNO\n" +
                    "  and e.EMPNO = ep.EMPNO\n" +
                    "  and e.LASTNAME = 'HAAS'";
            resultSet = statement.executeQuery(st);
            ArffLoader loader = new ArffLoader();
//            Instances dataSet = loader.getDataSet();
//            dataSet.setClassIndex(dataSet.numAttributes() - 1);
//            return dataSet;
            int row = 1;
            while (resultSet.next()) {
                System.out.print("Record " + (row++) + " : ");
                System.out.print(resultSet.getString("FIRSTNME") + ", ");
                System.out.print(resultSet.getString(2) + ", ");
                System.out.print(resultSet.getString(3) + ", ");
                System.out.print(resultSet.getInt(4) + ", ");
                System.out.println(resultSet.getDouble("SALARY"));
            }
            st = "insert into STUDENT values (101,'Jack'), (102,'Ann')";
            row = statement.executeUpdate(st);
            System.out.println(row + " are inserted");
            connection.close();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void train() {
        Instances training = getDataset(this.trainingFile);
        MultilayerPerceptron mlp = new MultilayerPerceptron();
        mlp.setLearningRate(0.1);
        mlp.setMomentum(0.2);
        mlp.setTrainingTime(10000);
        mlp.setHiddenLayers("41,11");
        mlp.setValidationThreshold(95);
        try {
            mlp.buildClassifier(training);
            Evaluation eval = new Evaluation(training);
            eval.evaluateModel(mlp, training);
            System.out.println(eval.toSummaryString());
            SerializationHelper.write(this.modelFile, mlp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void predict() {
        Instances predeict = getDataset(this.predictFile);
        try {
            MultilayerPerceptron mlp = (MultilayerPerceptron) SerializationHelper.read(this.modelFile);
            Attribute att = predeict.attribute(predeict.numAttributes() - 1);
            for (int i = 0; i < predeict.numInstances(); i++) {
                double value = mlp.classifyInstance(predeict.instance(i));
                System.out.println(predeict.instance(i).toString() + " = " + att.value((int) value) + "(" + value + ")");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
