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

    //Конструктор
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

        //TODO: запись в фалй через GSON и чтение его и перевод в нормальный формат
     //   String jsonDB = new Gson().toJson(table);
     //   table = new Gson().fromJson(jsonDB,Hashtable.class);
        fileSize = 0;
        if (new File(baseDir+fileName).exists()) {

            //Если файл существует читаем его и меняем размер
            readDB();
            changeFileSize();
        }else{

            //Создание файла и пути до файла если не существует
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


    //читает из файла с именем dbName базу данных
    private void readDB(){
        table = new Hashtable<>();

    }

    //изменяет информацию о размере файла
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

    //Добавляет пару ключ, значение
    public void append(String key, String value){
        //TODO: запись в файл
        table.put(key, value);
    }

    //Возвращает значение по ключу
    public ResponseItem getValue(String key){
        ResponseItem item = new ResponseItem();
        item.ResponseItemList.add(new Item(key,table.get(key)));
        return item;
    }

    //удаляет запись с заданным ключом
    //TODO: удаление из файла, пока перезапись
    public void delKey(String key){
        table.remove(key);
    }


    //удаляет все записи с заданным значением
    public void delValue(String value){
        Enumeration<String> keys = table.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            if(table.get(key) == value) {
                delKey(key);
            }
        }


    }

    //Возвращает все пары ключ значение в виде:
    //String = key1:value1 key2:value2 ...
    public ResponseItem getAll(){
        ResponseItem items = new ResponseItem();
        for (Map.Entry<String, String> entry : table.entrySet()) {
 //           s += entry.getKey() + ":" + entry.getValue() + " ";
            items.ResponseItemList.add(new Item(entry.getKey(),entry.getValue()));
        }
        return items;
    }

    //возможно тоже не понадобится
    public String toString(){
        return table.toString();
    }

    //Возвращает строку, содержащую все ключи у которых заданное значение
    //String = "key1 key2 ..."
    public ResponseItem getKeys(String value){
        ResponseItem items = new ResponseItem();
        Enumeration<String> keys = table.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (table.get(key) == value) {
  //              s = s + key + " ";
                items.ResponseItemList.add(new Item(key,value));
            }
        }
        return items;
    }




    //функция для тестирования класса
    public static void main(String[] args) {
        NoSqlDB db = new NoSqlDB("test");

        db.append("test","oleg");
        db.append("test1","oleg");
        db.append("test3","alesha");

      //  db.delValue("oleg");
        String s = db.toString();
        ResponseItem item = db.getKeys("oleg");
        for(int i = 0; i < item.ResponseItemList.size(); i++){
            System.out.print(item.ResponseItemList.get(i).toString());
        }


         ResponseItem item2 = db.getValue("test3");
        System.out.println(item2.ResponseItemList.get(0).toString());
    //    System.out.println(db.getAll());


        ResponseItem item3 = db.getAll();
        for(int i = 0; i < item3.ResponseItemList.size(); i++){
            System.out.print(item3.ResponseItemList.get(i).toString());
        }


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




