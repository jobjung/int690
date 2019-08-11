import java.io.*;
import java.util.ArrayList;

public class lab2 {

    public static void main(String[] args) {
        ArrayList<CausesOfDeath> list = new ArrayList<CausesOfDeath>(1100);
        readBuffer("CausesOfDeath_France_2001-2008.csv", list);
        String fileRaf = "CausesOfDeath_France_2001-2008.raf";
        writeRandomAccessFile(fileRaf, list);
        System.out.println(readRAFbyRecordNumber(fileRaf, 1056));
        System.out.println(sumOfDeath(fileRaf, 2007));
    }

    public static void readBuffer(String filename, ArrayList list) {
        FileReader fr;
        try {
            fr = new FileReader(filename);
            BufferedReader bufR = new BufferedReader(fr);
            String line = "";
            bufR.readLine();
            while ((line = bufR.readLine()) != null) {
                processLine(line, list);
            }
            fr.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void processLine(String s, ArrayList<CausesOfDeath> list) {
        String[] dataInLine = s.split(",\"");
        dataInLine[0] = dataInLine[0].replaceAll("\"", "");
        dataInLine[1] = dataInLine[1].replaceAll("\"", "");
        dataInLine[3] = dataInLine[3].replaceAll("\"", "");
        dataInLine[5] = dataInLine[5].replaceAll("\"", "");
        dataInLine[6] = dataInLine[6].replaceAll("\"| ", "");
        dataInLine[6] = dataInLine[6].replaceAll(":", "0");
        CausesOfDeath causesOfDeath = new CausesOfDeath(
                Integer.parseInt(dataInLine[0]),
                dataInLine[1],
                dataInLine[3],
                dataInLine[5],
                Integer.parseInt(dataInLine[6]));
        list.add(causesOfDeath);
    }

    public static void writeRandomAccessFile(String filename, ArrayList<CausesOfDeath> list) {
        try {
            RandomAccessFile raf = new RandomAccessFile(filename, "rw");
            for (CausesOfDeath cod : list) {
                raf.writeInt(cod.getTime());
                raf.write(cod.getGeoInBytes());
                raf.write(cod.getSexInBytes());
                raf.write(cod.getIcd10InBytes());
                raf.writeInt(cod.getValue());
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static CausesOfDeath readRAFbyRecordNumber(String filename, int recordNumber) {
        CausesOfDeath s = new CausesOfDeath();
        try {
            RandomAccessFile raf = new RandomAccessFile(filename, "r");
            long postion = (recordNumber - 1) * (Integer.BYTES + s.GEO_SIZE + s.SEX_SIZE + s.ICD10_SIZE + Integer.BYTES);
            raf.seek(postion);
            s.setTime(raf.readInt());
            byte[] geo = new byte[s.GEO_SIZE];
            raf.read(geo);
            s.setGeo(new String(geo).trim());
            byte[] sex = new byte[s.SEX_SIZE];
            raf.read(sex);
            s.setSex(new String(sex).trim());
            byte[] icd10 = new byte[s.ICD10_SIZE];
            raf.read(icd10);
            s.setIcd10(new String(icd10).trim());
            s.setValue(raf.readInt());
            raf.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return s;
    }

    public static int sumOfDeath(String filename, int time) {
        CausesOfDeath s = new CausesOfDeath();
        int sum = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile(filename, "r");
            int postion = 0;
            while (postion < raf.length()) {
                raf.seek(postion);
                if (raf.readInt() == time) {
                    sum++;
                }
                postion += Integer.BYTES+s.GEO_SIZE + s.SEX_SIZE + s.ICD10_SIZE + Integer.BYTES;
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sum;
    }
}

class CausesOfDeath {
    int time;
    String geo;
    String sex;
    String icd10;
    int value;
    final int GEO_SIZE = 6;
    final int SEX_SIZE = 7;
    final int ICD10_SIZE = 101;

    public CausesOfDeath() {
    }

    public CausesOfDeath(int time, String geo, String sex, String icd10, int value) {
        this.time = time;
        this.geo = geo;
        this.sex = sex;
        this.icd10 = icd10;
        this.value = value;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIcd10() {
        return icd10;
    }

    public void setIcd10(String icd10) {
        this.icd10 = icd10;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CausesOfDeath{" +
                "time=" + time +
                ", geo='" + geo + '\'' +
                ", sex='" + sex + '\'' +
                ", icd10='" + icd10 + '\'' +
                ", value=" + value +
                '}';
    }

    public byte[] getGeoInBytes() {
        byte[] itemBytes = new byte[GEO_SIZE];
        System.arraycopy(geo.getBytes(), 0, itemBytes, 0, geo.length());
        return itemBytes;
    }

    public byte[] getSexInBytes() {
        byte[] itemBytes = new byte[SEX_SIZE];
        System.arraycopy(sex.getBytes(), 0, itemBytes, 0, sex.length());
        return itemBytes;
    }

    public byte[] getIcd10InBytes() {
        byte[] itemBytes = new byte[ICD10_SIZE];
        System.arraycopy(icd10.getBytes(), 0, itemBytes, 0, icd10.length());
        return itemBytes;
    }

}
