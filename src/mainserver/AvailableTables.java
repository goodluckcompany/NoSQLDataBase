package mainserver;

import java.io.*;
import java.util.Vector;

/**
 * Created by Anna on 24.11.2015.
 */
public class AvailableTables {

    String fileName = "src\\mainserver\\AvailableTables";
    Vector<Vector<String>> table;
    Vector<String> row;

    AvailableTables(){//инициализация списка таблиц, считываем из файла
        table = new Vector<Vector<String>>();
        Vector<String> vs;
        String line;

        try {//пытаемся открыть файл для чтения
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

    public int addTable(String tableName, String mainServerIP, String reserveServerIP){//добавить таблицу в список таблиц
        for(int i = 0; i < table.size(); i++){
            if(table.get(i).get(0).equals(tableName))
                return -1; //возвращает -1, если такая таблица уже есть в списке
        }
        row = new Vector<String>();
        StringBuilder sb = new StringBuilder();
        sb.append(tableName + ',' + mainServerIP + ',' + reserveServerIP + "\r\n");
        row.add(tableName);
        row.add(mainServerIP);
        row.add(reserveServerIP);
        table.add(row);
        return 0;
    }

    public void removeTable(String tableName){//удалить таблицу из списка таблиц
        for(int i = 0; i < table.size(); i++){
            if (table.get(i).get(0).equals(tableName))
                table.remove(i);
        }
    }


    public void saveTable(){//запись в файл таблицы
        FileWriter fw;
        try { //Пытаемся открыть файл на запись
            fw = new FileWriter(new File(fileName));
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < table.size(); i++){
                sb.append(table.get(i).get(0) + ',' + table.get(i).get(1) + ',' + table.get(i).get(2) + "\r\n");
            }

            try { //Пытаемся писать в файл
                fw.write(sb.toString()); //записывем собранную строку в файл
                fw.close();
            }catch (IOException e) {
                e.printStackTrace();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMainServerIP(String tableName){//возвращает IP главного сервера для таблицы
        String mainServerIP = "";
        for(int i = 0; i < table.size(); i++){
            if (table.get(i).get(0).equals(tableName))
                mainServerIP = table.get(i).get(1);
        }
        return mainServerIP;
    }

    public String getReserveServerIP(String tableName){ //возвращает IP резервного сервера для таблицы
        String reserveServerIP = "";
        for(int i = 0; i < table.size(); i++){
            if (table.get(i).get(0).equals(tableName))
                reserveServerIP = table.get(i).get(2);
        }
        return reserveServerIP;
    }

}
