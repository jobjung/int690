package com.company;

import net.sf.saxon.xqj.SaxonXQDataSource;

import javax.xml.xquery.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class lab51 {
    public static void main(String[] args) {
        try {
//            InputStream inputStream = new FileInputStream(new File("stock.xqy"));
//            InputStream inputStream = new FileInputStream(new File("stockprice.xqy"));
//            InputStream inputStream = new FileInputStream(new File("stockpricemorethan140.xqy"));
//            InputStream inputStream = new FileInputStream(new File("stockmaxprice.xqy"));
            InputStream inputStream = new FileInputStream(new File("stockmaxpriceeachcompany.xqy"));
            XQDataSource dataSource = new SaxonXQDataSource();
            XQConnection connection = dataSource.getConnection();
            XQPreparedExpression preparedExpression = connection.prepareExpression(inputStream);
            XQResultSequence resultSequence = preparedExpression.executeQuery();
            while (resultSequence.next()) {
                System.out.println(resultSequence.getItemAsString(null));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XQException e) {
            e.printStackTrace();
        }
    }
}
