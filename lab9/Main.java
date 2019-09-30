package com.company;

import javax.swing.*;

public class Main {
    static Classification_Logistics_Loan loan;
    public static void main(String[] args) {
        loan = new Classification_Logistics_Loan(
                "creditRisk_Clean_NoCreditHistory_training.arff",
                "creditRisk_Clean_NoCreditHistory_testing.arff",
                "creditRisk_Clean_NoCreditHistory_predict.arff",
                9
        );
        loan.trainingAndTesting();
        loan.predictDataSet();


        JFrame frame = new JFrame("LoanApp");
        frame.setContentPane(new LoanApp().JPanel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
