package com.company;

import net.sf.saxon.xqj.SaxonXQDataSource;

import javax.xml.xquery.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class homework5 {

    public static void main(String[] args) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("NASAtemperatureAbnormal.xqy"));
            XQDataSource dataSource = new SaxonXQDataSource();
            XQConnection connection = dataSource.getConnection();
            XQPreparedExpression preparedExpression = connection.prepareExpression(inputStream);
            XQResultSequence resultSequence = preparedExpression.executeQuery();
            Map<String, MaxMin> hMap = new LinkedHashMap<>(300);
            while (resultSequence.next()) {
//                System.out.println(resultSequence.getItemAsString(null));
                String[] line = resultSequence.getItemAsString(null).split(",");
                if (line.length == 3) {
                    if (!hMap.containsKey(line[0])) {
                        hMap.put(line[0], new MaxMin(line[1], Double.parseDouble(line[2])));
                    } else {
                        MaxMin mm = hMap.get(line[0]);
                        mm.Check(line[1], Double.parseDouble(line[2]));
                    }
                }
            }
            for (Map.Entry<String, MaxMin> entry : hMap.entrySet()) {
                System.out.print("Year : " + entry.getKey() + ", ");
                MaxMin mm = entry.getValue();
                System.out.print("Min Value : " + mm.getMin() + ", ");
                System.out.print("The Month of Min : " + mm.getMonthOfMin().toString() + ", ");
                System.out.print("Max Value : " + mm.getMax() + ", ");
                System.out.print("The Month of Max : " + mm.getMonthOfMax().toString());
                System.out.println();
            }
        } catch (FileNotFoundException | XQException e) {
            e.printStackTrace();
        }
    }
}

class MaxMin {
    double Max;
    double Min;

    public List<String> getMonthOfMin() {
        return MonthOfMin;
    }

    public List<String> getMonthOfMax() {
        return MonthOfMax;
    }

    List<String> MonthOfMin;
    List<String> MonthOfMax;

    public MaxMin(String month, double init) {
        Max = Min = init;
        MonthOfMin = new ArrayList<>(12);
        MonthOfMin.add(month);
        MonthOfMax = new ArrayList<>(12);
        MonthOfMax.add(month);
    }

    public double getMax() {
        return Max;
    }

    public void setMax(double max) {
        Max = max;
    }

    public double getMin() {
        return Min;
    }

    public void setMin(double min) {
        Min = min;
    }

    public void Check(String month, double value) {
        if (value < Min) {
            Min = value;
            MonthOfMin.clear();
            MonthOfMin.add(month);
        } else if (value == Min) {
            MonthOfMin.add(month);
        }
        if (value > Max) {
            Max = value;
            MonthOfMax.clear();
            MonthOfMax.add(month);
        } else if (value == Max) {
            MonthOfMax.add(month);
        }
    }
}
