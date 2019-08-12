package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class homework3 {

    public static void main(String[] args) {
        String connectionURL = "jdbc:db2://10.4.53.14:50000/SAMPLE";
        Connection connection;
        java.sql.Statement statement;
        java.sql.ResultSet resultSet;

        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
            connection = DriverManager.getConnection(connectionURL, "stds61302", "ds690");
            statement = connection.createStatement();
            String st = "drop table HOMEWORK3\n";
            statement.executeUpdate(st);
            st = "create table HOMEWORK3(\n" +
                    "id int generated always as identity\n" +
                    "constraint homework3_pk\n" +
                    "primary key,\n" +
                    "name char(20))";
            int row = 1;
            statement.executeUpdate(st);
            st = "insert into HOMEWORK3 (name) values ('hw1'), ('hw2'), ('hw3')";
            row = statement.executeUpdate(st);
            System.out.println(row + " are inserted");
            row = 1;
            st = "select ID,NAME from HOMEWORK3";
            resultSet = statement.executeQuery(st);
            while (resultSet.next()) {
                System.out.print("Record " + (row++) + " : ");
                System.out.print(resultSet.getString("ID") + ", ");
                System.out.print(resultSet.getString(2));
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
