package com.company;


import java.sql.*;

public class lab52 {
    public static void main(String[] args) {
        String connectionURL = "jdbc:db2://10.4.53.14:50000/SAMPLE";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
            connection = DriverManager.getConnection(connectionURL, "stds61302", "ds690");
            statement = connection.createStatement();
            String st = "select POID, CUSTID,\n" +
                    "       xmlquery('for $item in $doc/PurchaseOrder/item\n" +
                    "                 where $item/price> 9\n" +
                    "                 return $item/name/text()'\n" +
                    "           passing PORDER as \"doc\") por\n" +
                    "from DB2INST1.PURCHASEORDER\n" +
                    "where CUSTID=1002";
            resultSet = statement.executeQuery(st);
            int row = 1;
            while (resultSet.next()) {
                System.out.print("Record " + (row++) + " : ");
                System.out.print(resultSet.getInt("POID") + ", ");
                System.out.print(resultSet.getInt("CUSTID") + ", ");
                System.out.print(resultSet.getString("por"));
                System.out.println();
            }

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
    }
}
