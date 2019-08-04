package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author 61130700302
 */
public class lab1 {

    public static void main(String[] args) {
        Map<Integer, Sale> hMap = new HashMap<Integer, Sale>(100000);
        try {
            FileReader reader = new FileReader("Sale.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataLine = line.split(",'");
                int saleId = Integer.parseInt(dataLine[0].substring(1));
                String saleTransaction = dataLine[1].substring(0,dataLine[1].length()-1);
                String saleItem = dataLine[2].substring(0,dataLine[2].length()-3);
                hMap.put(saleId,new Sale(saleId,saleTransaction,saleItem));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Integer, Sale> entry : hMap.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue().saleTransaction+" "+entry.getValue().saleItem);
        }
    }
}

class Sale {
    int saleId;
    String saleTransaction;
    String saleItem;

    public Sale() {
    }

    public Sale(int saleId, String saleTransaction, String saleItem) {
        this.saleId = saleId;
        this.saleTransaction = saleTransaction;
        this.saleItem = saleItem;

    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public String getSaleTransaction() {
        return saleTransaction;
    }

    public void setSaleTransaction(String saleTransaction) {
        this.saleTransaction = saleTransaction;
    }

    public String getSaleItem() {
        return saleItem;
    }

    public void setSaleItem(String saleItem) {
        this.saleItem = saleItem;
    }
}