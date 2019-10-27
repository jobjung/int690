package com.company;

public class Main {

    public static void main(String[] args) {

        NeuralNetwork nn = new NeuralNetwork("cropPriceTraining.arff", "cropPricePredict.arff", "cropPrice.model");
//        NeuralNetwork nn = new NeuralNetwork("creditRisk_Clean_NoCreditHistory_training.arff", "creditRisk_Clean_NoCreditHistory_predict.arff", "creditRisk.model");
//        nn.train();
        nn.predict();


    }
}
