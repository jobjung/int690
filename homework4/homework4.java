package com.company;

import net.sf.saxon.xqj.SaxonXQDataSource;

import javax.xml.xquery.*;

public class homework4 {
    public static void main(String[] args) {
        try {
            XQDataSource dataSource = new SaxonXQDataSource();
            XQConnection xqConnection = dataSource.getConnection();
            XQExpression xqExpression = xqConnection.createExpression();

            XQSequence xqSequence = xqExpression.executeQuery("doc('resume_w_xsl.xml')/resumes/person/@name/string()");
            while (xqSequence.next()) {
                System.out.println("Name : "+xqSequence.getItemAsString(null));
            }
            xqSequence = xqExpression.executeQuery("doc('resume_w_xsl.xml')/resumes/person/phone");
            while (xqSequence.next()) {
                System.out.println(xqSequence.getItemAsString(null));
            }
            xqSequence = xqExpression.executeQuery("doc('resume_w_xsl.xml')/resumes/person/project[contains(@start,'1996')]");
            while (xqSequence.next()) {
                System.out.println(xqSequence.getItemAsString(null));
            }
            xqConnection.close();
        } catch (XQException e) {
            e.printStackTrace();
        }
    }

}
