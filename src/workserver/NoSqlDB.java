package workserver;

/**
 * Created by homie on 22.11.2015.
 */

import com.google.gson.Gson;

import java.util.*;
import java.io.*;


public class NoSqlDB {

    /** Класс служит для создания таблицы и работой с ней: <br>
     * {@link NoSqlDB#dbName}, {@link NoSqlDB#table}, {@link NoSqlDB#fileSize}, {@link NoSqlDB#dir}.
     * @author Ivanov Aleksey
     */

    /** Имя таблицы, используется для создания файла*/
    private String dbName;
    /** Таблица с полями ключ, значение. Используется для хранения таблицы */
    private Hashtable<String, String> table;
    /** Размер файла, хранит размер файла, который находится в директории {@link NoSqlDB#dir}.   */
    private long fileSize;
    /**Путь до файла, используется для доступа к файлу */
    private String dir;

    //Конструктор

    /**
     * Конструктор класса {@link NoSqlDB}
     * @param dbName имя таблицы для создания
     */
    public NoSqlDB(String dbName){
        if(dbName.equals("")) throw new NullPointerException("Error: Null name");
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

    /**
     * Производит чтение из файла по пути {@link NoSqlDB#dir} в {@link NoSqlDB#table}.
     */
    private void readDB(){
        String jsonDB = "";
        try(FileReader reader = new FileReader(dir))
        {
            /*
            int c;
            while((c=reader.read())!=-1){

                jsonDB = jsonDB + (char) c;

            } */
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            jsonDB = stringBuffer.toString();
            reader.close();
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

    /**
     * Изменяет значение {@link NoSqlDB#fileSize} в зависимости от размера файла по пути {@link NoSqlDB#dir}
     */
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


    /**
     *
     * @return возвращает размер файла по пути {@link NoSqlDB#dir}
     */
    public long getFileSize(){
       return fileSize;
   }

    //запись таблицы в файл

    /**
     * Записывает таблицу {@link NoSqlDB#table} в файл {@link NoSqlDB#dir}.
     */
    private void writeTableToFile(){
        FileWriter writeFile = null;
        try {
            String jsonDB = new Gson().toJson(table);
            File tableFile = new File(dir);
            writeFile = new FileWriter(tableFile);
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

    /**
     *
     * @return возвращает имя таблицы
     */
    public String getDbName(){
        return dbName;
    }

    //Добавляет пару ключ, значение

    /**
     * Функция добавления в таблицу новой пары - ключ, значение. Так же записывает таблицу в файл и меняет его размер
     * для класса.
     * @param key ключ
     * @param value значение
     */
    public void append(String key, String value){

        //изменяем таблицу
        table.put(key, value);
        //запись в файл
        writeTableToFile();
        changeFileSize();
    }

    //Возвращает значение по ключу

    /**
     * Возвращает по ключу {@link ResponseItem} в который записывается пара значение и ключ
     * @param key ключ по которому искать значение
     * @return Возвращает экземпляр класса {@link ResponseItem}.
     */
    public ResponseItem getValue(String key){
        ResponseItem item = new ResponseItem();
        if(table.get(key) != null) {
            item.ResponseItemList.add(new Item(key,table.get(key)));
        }
        return item;
    }

    //удаляет запись с заданным ключом

    /**
     * Удаляет запись с заданным ключом, так же перезаписывает файл и меняет размер
     * @param key ключ, который следует удалить
     */
    public void delKey(String key){
        table.remove(key);
        //изменение файла
        writeTableToFile();
        changeFileSize();
    }


    //удаляет все записи с заданным значением

    /**
     * Удаляет все записи с заданным значением, так же перезаписывает файл и меняет размер
     * @param value значение для удаления
     */
    public void delValue(String value){
        Enumeration<String> keys = table.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            if(table.get(key).compareTo(value) == 0) {
                delKey(key);
            }
        }

        writeTableToFile();
        changeFileSize();

    }

    //Возвращает все пары ключ значение в классе ResponseItem

    /**
     * Возвращает все пары ключ - значение в классе {@link ResponseItem}
     * @return экземпляр класса {@link ResponseItem}
     */
    public ResponseItem getAll(){
        ResponseItem items = new ResponseItem();
        for (Map.Entry<String, String> entry : table.entrySet()) {
            items.ResponseItemList.add(new Item(entry.getKey(),entry.getValue()));
        }
        return items;
    }

    //возможно тоже не понадобится

    /** @deprecated Возвращение строкового значения таблицы
    * @return Возвращает строкое значение таблицы {@link NoSqlDB#table}
     */
    public String toString(){
        if(table == null) return "";
        else return table.toString();
    }

    //Возвращает ResponseItem, содержащий все ключи у которых заданное значение

    /**
     * Возвращает {@link ResponseItem}, содержащий все ключи у которых заданное значение
     * @param value значение ключи которого требуется вернуть
     * @return экземпляр класса {@link ResponseItem}
     */
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

    //Удаляет файл базы данных

    /**
     * Удаляет файл {@link NoSqlDB#dir}
     */
    public void deleteFile(){
        File tableFile = new File(dir);
        tableFile.delete();
    }




    //функция для тестирования класса
    public static void main(String[] args) {
        NoSqlDB db = new NoSqlDB("test");

        db.append("test","oleg");
        db.append("test1","oleg");
        db.append("test3","alesha");

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

        db.deleteFile();

    }


}




