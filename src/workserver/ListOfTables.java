package workserver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by алексей on 14.12.2015.
 */
public class ListOfTables {
    /** Класс служит для хранения имён таблиц, который получается из заданной директории: <br>
     * {@link ListOfTables#tables}, {@link ListOfTables#size}, {@link ListOfTables#dir} .
     * @author Ivanov Aleksey
     */

    /** Используется для хранения имён таблиц из директории {@link ListOfTables#dir}    */
    private ArrayList<String> tables;
    /** Размер файлов в директории {@link ListOfTables#dir}    */
    private long size;
    /** Директория из которой загружать имена файлов  */
    private  static String dir;

    /**
     * Конструктор, задает директорию и вызывает функцию {@link ListOfTables#update()} для получения имён файлов
     */
    ListOfTables() {
        dir = "";
        try {
            dir = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dir += File.separator + "Databases";
        tables = new ArrayList<>();
        size = 0 ;
        update();

    }

    /**
     * Очищает {@link ListOfTables#tables} и вызывает функцию {@link ListOfTables#listFilesForFolder(File)}
     * для получения списка файлов из директории {@link ListOfTables#dir}
     */
    public void update(){
        tables.clear();
        final File folder = new File(dir);
        listFilesForFolder(folder);

    }

    /**
     * Производит поиск файлов в директории {@link ListOfTables#dir} и добавляет их в список если их расширение
     * .nosql
     * @param folder директория из которой получать список имён
     */
    private void listFilesForFolder(final File folder) {
        try {
            size = 0;
            if(folder.listFiles() == null) return;

            for (final File fileEntry : folder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    String s = fileEntry.getName();
                    if (s.contains(".nosql")) {
                        s = s.replace(".nosql", "");
                        size += fileEntry.length();
                        tables.add(s);
                    }

                }
            }
        }catch (NullPointerException e)
        {
            System.out.println(e);
        }
    }

    /**
     * Возвращает размер файлов директории {@link ListOfTables#dir}
     * @return возвращает {@link ListOfTables#size}
     */
    public long getSize(){ return size;}

    /**
     * Возвращает список имён файлов в {@link ListOfTables#dir}
     * @return возвращает {@link ListOfTables#tables}
     */
    public ArrayList<String> getTables() {
        return tables;
    }


    public static void main(String[] args) {

        ListOfTables test = new ListOfTables();

        ArrayList<String> tables = test.getTables();



        for(String s: tables){
            System.out.println(s);
        }

        long size  = test.getSize();
        System.out.println(size);


    }


}
