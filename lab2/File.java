/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class File {

    public static void main(String[] args) {
        //String file = "Sale.txt";
        String file = "Sale.raf";
        ArrayList<sale> list = new ArrayList<sale>(100000);
        readRandomAccessFile(file, list);
        System.out.println(list.get(100).toString());
        writeRandomAccessFile("Sale2.raf", list);
        file = "Sale2.raf";
        list = new ArrayList<sale>(100000);
        readRandomAccessFile(file, list);
        System.out.println(list.get(100).toString());
    }

    public static void readDataInputStream(String file, ArrayList<sale> list) {
        FileInputStream fs;
        try {
            fs = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(fs);
            String line = "";
            while ((line = dataInputStream.readLine()) != null) {
                processLine(line, list);
            }
            fs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void readBufferedReader(String file, ArrayList<sale> list) {
        FileReader fr;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                processLine(line, list);
            }
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void processLine(String line, ArrayList<sale> list) {
        String[] dataLine = new String[3];
        dataLine = line.split(",'");
        dataLine[0] = dataLine[0].substring(1);
        dataLine[1] = dataLine[1].replace("'", "");
        dataLine[2] = dataLine[2].substring(0, dataLine[2].length() - 3);
        list.add(new sale(Integer.valueOf(dataLine[0]), dataLine[1], dataLine[2]));
    }

    public static void readRandomAccessFile(String file, ArrayList<sale> list) {
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(file, "rw");

            while (raf.getFilePointer() != raf.length()) {
                sale s = new sale();
                int id = raf.readInt();
                s.setSaleId(id);
                byte[] transaction = new byte[s.Transaction_SIZE];
                raf.read(transaction);
                s.setSaleTransaction(new String(transaction));
                byte[] item = new byte[s.ITEM_SIZE];
                raf.read(item);
                s.setItem(new String(item));
                list.add(s);
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static sale readEntryRandomAccessFile(String file, int record) {
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(file, "rw");
            sale s = new sale();
            raf.seek((Integer.BYTES + s.Transaction_SIZE + s.ITEM_SIZE) * record);
            int id = raf.readInt();
            s.setSaleId(id);
            byte[] transaction = new byte[s.Transaction_SIZE];
            raf.read(transaction);
            s.setSaleTransaction(new String(transaction));
            byte[] item = new byte[s.ITEM_SIZE];
            raf.read(item);
            s.setItem(new String(item));
            raf.close();
            return s;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void writeRandomAccessFile(String file, ArrayList<sale> list) {
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(file, "rw");
            for (sale s : list) {
                raf.write(s.getSaleId());
                raf.write(s.getSaleTransactionInBytes());
                raf.write(s.getItemInBytes());
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class sale {

    int saleId;
    String saleTransaction;
    String item;
    final int Transaction_SIZE = 20;
    final int ITEM_SIZE = 50;

    public sale(int saleId, String saleTransaction, String item) {
        this.saleId = saleId;
        this.saleTransaction = saleTransaction;
        this.item = item;
    }

    sale() {
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

    public byte[] getSaleTransactionInBytes() {
        byte[] tb = new byte[Transaction_SIZE];
        System.arraycopy(saleTransaction.getBytes(), 0, tb, 0, saleTransaction.length());
        return tb;
    }

    public void setSaleTransaction(String saleTransaction) {
        this.saleTransaction = saleTransaction;
    }

    public String getItem() {
        return item;
    }

    public byte[] getItemInBytes() {
        byte[] tb = new byte[ITEM_SIZE];
        System.arraycopy(item.getBytes(), 0, tb, 0, item.length());
        return tb;
    }

    @Override
    public String toString() {
        return "sale{" + "saleId=" + saleId + ", saleTransaction=" + saleTransaction + ", item=" + item + '}';
    }

    public void setItem(String item) {
        this.item = item;
    }

}
