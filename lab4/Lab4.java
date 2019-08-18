package com.company;

import net.sf.saxon.xqj.SaxonXQDataSource;

import javax.xml.xquery.*;
import java.io.*;

public class Lab4 {
    public static void main(String[] args) {
        try {
            InputStream inputStream = new FileInputStream(new File("stock.xml"));
            XQDataSource dataSource = new SaxonXQDataSource();
            XQConnection xqConnection = dataSource.getConnection();
            XQPreparedExpression preparedExpression = xqConnection.prepareExpression(inputStream);
            XQResultSequence resultSequence = preparedExpression.executeQuery();
            while (resultSequence.next()) {
                System.out.println(resultSequence.getItemAsString(null));
            }
            System.out.println("Choose only some company");
            XQExpression xqExpression = xqConnection.createExpression();
            XQSequence xqSequence = xqExpression.executeQuery("doc('stock.xml')/stocks/stock/company");
            while (xqSequence.next()) {
                System.out.println(xqSequence.getItemAsString(null));
            }
            System.out.println("Any companies");
            XQSequence xqSequence2 = xqExpression.executeQuery("doc('stock.xml')//company");
            while (xqSequence2.next()) {
                System.out.println(xqSequence2.getItemAsString(null));
            }
            System.out.println("Any companies under /stocks/stock");
            XQSequence xqSequence3 = xqExpression.executeQuery("doc('stock.xml')/stocks/stock//company");
            while (xqSequence3.next()) {
                System.out.println(xqSequence3.getItemAsString(null));
            }
            System.out.println("Show company info of the second stock.");
            XQSequence xqSequence4 = xqExpression.executeQuery("doc('stock.xml')/stocks/stock[2]/company");
            while (xqSequence4.next()) {
                System.out.println(xqSequence4.getItemAsString(null));
            }
            System.out.println("Show Banking company info.");
            XQSequence xqSequence5 = xqExpression.executeQuery("doc('stock.xml')/stocks/stock[category='Banking']/company");
            while (xqSequence5.next()) {
                System.out.println(xqSequence5.getItemAsString(null));
            }
            System.out.println("Show Banking company info. Show only text without tag");
            XQSequence xqSequence6 = xqExpression.executeQuery("doc('stock.xml')/stocks/stock[category='Banking']/company/abbreviattion/text()");
            while (xqSequence6.next()) {
                System.out.println(xqSequence6.getItemAsString(null));
            }
            System.out.println("Show Attribute");
            XQSequence xqSequence7 = xqExpression.executeQuery("doc('stock.xml')/stocks/stock/category/@ID/string()");
            while (xqSequence7.next()) {
                System.out.println(xqSequence7.getItemAsString(null));
            }
            xqConnection.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XQException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
