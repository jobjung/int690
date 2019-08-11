package com.company;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class Lab3 {
    public static void main(String[] args) {
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

        cassandraConnect();
    }

    public static void cassandraConnect() {
        String nodeIp = "10.4.53.7";
        Cluster.Builder clusterBuilder = Cluster.builder()
                .addContactPoint(nodeIp)
                .withPort(9042);
        Cluster cluster = clusterBuilder.build();
        Metadata metadata = cluster.getMetadata();
        System.out.println("Connected to Cluster: " + metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.println("Datacenter: " + host.getDatacenter() + ", hots: " + host.getAddress() + ", " + host.getRack());
        }
        Session session = cluster.connect();
        session.execute("use salekeyspace");
        com.datastax.driver.core.ResultSet resultSet = session.execute("select * from emptimesheetbydate");
        resultSet.forEach(row -> {
            System.out.print("emp id is " + row.getInt("emp_id")+" ");
            System.out.print("date: " + row.getDate("workdate").toString()+" ");
            System.out.print("start time: " + row.getTime("start_time") + " ");
            Set<String> emails = row.getSet("email",String.class);
            for (String email : emails){
                System.out.print(email+", ");
            }
            List<String> locations = row.getList("location",String.class);
            for (String location : locations){
                System.out.print(location+", ");
            }
            System.out.println();
        });
        cluster.close();
    }
}
