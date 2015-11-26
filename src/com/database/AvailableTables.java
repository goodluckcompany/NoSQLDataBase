package com.database;

import java.io.*;
import java.util.Vector;

/**
 * Created by Anna on 24.11.2015.
 */
public class AvailableTables {

    String fileName = "src\\com\\database\\AvailableTables";
    Vector<Vector<String>> table;
    Vector<String> row;

    AvailableTables(){//инициализаци€ списка таблиц, считываем из файла
        table = new Vector<Vector<String>>();
        Vector<String> vs;
        String line;

        try {//пытаемс€ открыть файл дл€ чтени€
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            StringBuilder sb = null;

            while ((line = br.readLine()) != null) {
                sb = new StringBuilder();
                row = new Vector<String>();

                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) != ',')
                        sb.append(line.charAt(i));
                    else {
                        row.add(sb.toString());
                        sb=null;
                        sb = new StringBuilder();
                    }
                }

                row.add(sb.toString());
                table.add(row);
                row = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTable(String tableName, String mainServerIP, String reserveServerIP){//добавить таблицу в список таблиц
        row = new Vector<String>();
        StringBuilder sb = new StringBuilder();
        sb.append(tableName + ',' + mainServerIP + ',' + reserveServerIP + "\r\n");
        row.add(tableName);
        row.add(mainServerIP);
        row.add(reserveServerIP);
        table.add(row);
    }

    public void removeTable(String tableName){//удалить таблицу из списка таблиц
        for(int i = 0; i < table.size(); i++){
            if (table.get(i).get(0).equals(tableName))
                table.remove(i);
        }
    }


    public void saveTable(){//запись в файл таблицы
        FileWriter fw;
        try { //ѕытаемс€ открыть файл на запись
            fw = new FileWriter(new File(fileName));
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < table.size(); i++){
                sb.append(table.get(i).get(0) + ',' + table.get(i).get(1) + ',' + table.get(i).get(2) + "\r\n");
            }

            try { //ѕытаемс€ писать в файл
                fw.write(sb.toString()); //записывем собранную строку в файл
                fw.close();
            }catch (IOException e) {
                e.printStackTrace();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMainServerIP(String tableName){//возвращает IP главного сервера дл€ таблицы
        String mainServerIP = "";
        for(int i = 0; i < table.size(); i++){
            if (table.get(i).get(0).equals(tableName))
                mainServerIP = table.get(i).get(1);
        }
        return mainServerIP;
    }

    public String getReserveServerIP(String tableName){ //возвращает IP резервного сервера дл€ таблицы
        String reserveServerIP = "";
        for(int i = 0; i < table.size(); i++){
            if (table.get(i).get(0).equals(tableName))
                reserveServerIP = table.get(i).get(2);
        }
        return reserveServerIP;
    }

}
