package workserver;

/**
 * Created by homie on 22.11.2015.
 */

import com.google.gson.Gson;

import java.util.*;
import java.io.*;


public class NoSqlDB {

    private String dbName;
    private Hashtable<String, String> table;
    private long fileSize;

    //�����������
    public NoSqlDB(String dbName){
        this.dbName = dbName;
        String fileName = dbName + ".nosql";
        String baseDir = "";
        try {
            baseDir = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        baseDir += File.separator + "Databases" + File.separator;

        //TODO: ������ � ���� ����� GSON � ������ ��� � ������� � ���������� ������
     //   String jsonDB = new Gson().toJson(table);
     //   table = new Gson().fromJson(jsonDB,Hashtable.class);
        fileSize = 0;
        if (new File(baseDir+fileName).exists()) {

            //���� ���� ���������� ������ ��� � ������ ������
            readDB();
            changeFileSize();
        }else{

            //�������� ����� � ���� �� ����� ���� �� ����������
            File dbFile = new File(baseDir);
            try {
                dbFile.mkdirs();
                dbFile = new File(baseDir+fileName);
                dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


            table = new Hashtable<>();
            changeFileSize();


        }
    }


    //������ �� ����� � ������ dbName ���� ������
    private void readDB(){
        table = new Hashtable<>();

    }

    //�������� ���������� � ������� �����
    private void changeFileSize(){

        String baseDir = "";
        try {
            baseDir = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        baseDir += File.separator + "Databases" + File.separator;
        File f = new File(baseDir + dbName + ".nosql");
        fileSize = f.length();

    }

   public long getFileSize(){
       return fileSize;
   }


    public String getDbName(){
        return dbName;
    }

    //��������� ���� ����, ��������
    public void append(String key, String value){
        //TODO: ������ � ����
        table.put(key, value);
    }

    //���������� �������� �� �����
    public String getValue(String key){
        return table.get(key);
    }

    //������� ������ � �������� ������
    //TODO: �������� �� �����, ���� ����������
    public void delKey(String key){
        table.remove(key);
    }


    //������� ��� ������ � �������� ���������
    public void delValue(String value){
        Enumeration<String> keys = table.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            if(table.get(key) == value) {
                delKey(key);
            }
        }


    }

    //���������� ��� ���� ���� �������� � ����:
    //String = key1:value1 key2:value2 ...
    public String getAll(){
        String s = "";
        for (Map.Entry<String, String> entry : table.entrySet()) {
            s += entry.getKey() + ":" + entry.getValue() + " ";
        }
        return s;
    }

    //�������� ���� �� �����������
    public String toString(){
        return table.toString();
    }

    //���������� ������, ���������� ��� ����� � ������� �������� ��������
    //String = "key1 key2 ..."
    public String getKeys(String value){
        String s = "";
        Enumeration<String> keys = table.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (table.get(key) == value) {
                s = s + key + " ";
            }
        }
        return s;
    }




    //������� ��� ������������ ������
    public static void main(String[] args) {
        NoSqlDB db = new NoSqlDB("test");

        db.append("test","oleg");
        db.append("test1","oleg");
        db.append("test3","alesha");

      //  db.delValue("oleg");
        String s = db.toString();
        String ss = db.getKeys("oleg");
        System.out.println(ss);


        System.out.println(db.getAll());



        Hashtable<String, String> test = new Hashtable<>();

        test.put("test", "oleg");
        test.put("test1", "oleg");
        test.put("test3", "alesha");

        System.out.println(test);

        String jsonDB = new Gson().toJson(test);
        System.out.println(jsonDB);
        Hashtable<String,String> test2 = new Gson().fromJson(jsonDB,Hashtable.class);
        System.out.println(test2.toString());

    }


}




