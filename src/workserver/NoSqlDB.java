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
    private String dir;

    //Конструктор
    public NoSqlDB(String dbName){
        this.dbName = dbName;
        dir = "";
        try {
            dir = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dir += File.separator + "Databases" + File.separator + dbName + ".nosql" ;


     //   String jsonDB = new Gson().toJson(table);
     //   table = new Gson().fromJson(jsonDB,Hashtable.class);

        fileSize = 0;
        if (new File(dir).exists()) {

            //Если файл существует читаем его и меняем размер
            readDB();
            changeFileSize();
        }else{

            //Создание файла и пути до файла если не существует
            File dbFile = new File(dir);
            try {
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


            table = new Hashtable<>();
            changeFileSize();


        }
    }


    //читает из файла с именем dbName базу данных из директории dir
    private void readDB(){
        String jsonDB = "";
        try(FileReader reader = new FileReader(dir))
        {
            // читаем посимвольно
            int c;
            while((c=reader.read())!=-1){

                jsonDB = jsonDB + (char) c;
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        if(jsonDB == ""){
            table = new Hashtable<>();
        }else {
            table = new Gson().fromJson(jsonDB, Hashtable.class);
        }
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

    //запись таблицы в файл
    private void writeTableToFile(){
        FileWriter writeFile = null;
        try {
            File tableFile = new File(dir);
            writeFile = new FileWriter(tableFile);
            String jsonDB = new Gson().toJson(table);
            writeFile.write(jsonDB);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writeFile != null) {
                try {
                    writeFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getDbName(){
        return dbName;
    }

    //Добавляет пару ключ, значение
    public void append(String key, String value){

        //изменяем таблицу
        table.put(key, value);
        //запись в файл
        writeTableToFile();
    }

    //Возвращает значение по ключу
    public ResponseItem getValue(String key){
        ResponseItem item = new ResponseItem();
        if(table.get(key) != null) {
            item.ResponseItemList.add(new Item(key,table.get(key)));
        }
        return item;
    }

    //удаляет запись с заданным ключом
    public void delKey(String key){
        table.remove(key);
        //изменение файла
        writeTableToFile();
    }


    //удаляет все записи с заданным значением
    public void delValue(String value){
        Enumeration<String> keys = table.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            if(table.get(key).compareTo(value) == 0) {
                delKey(key);
            }
        }

        writeTableToFile();


    }

    //Возвращает все пары ключ значение в классе ResponseItem
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
        if(table == null) return "";
        else return table.toString();
    }

    //Возвращает ResponseItem, содержащий все ключи у которых заданное значение
    public ResponseItem getKeys(String value){
        ResponseItem items = new ResponseItem();
        Enumeration<String> keys = table.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (table.get(key).compareTo(value) == 0) {
  //              s = s + key + " ";
                items.ResponseItemList.add(new Item(key,value));
            }
        }
        return items;
    }




    //функция для тестирования класса
    public static void main(String[] args) {
        NoSqlDB db = new NoSqlDB("test");

  //      db.append("test","oleg");
  //      db.append("test1","oleg");
  //      db.append("test3","alesha");

  //      db.delValue("oleg");
  //      db.delKey("test3");
   //     String s = db.toString();
        ResponseItem item = db.getKeys("oleg");
        for(int i = 0; i < item.ResponseItemList.size(); i++){
            System.out.print(item.ResponseItemList.get(i).toString());
        }


         ResponseItem item2 = db.getValue("test3");
        if(item2.ResponseItemList.size() != 0) System.out.println(item2.ResponseItemList.get(0).toString());
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




