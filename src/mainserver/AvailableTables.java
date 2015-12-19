package mainserver;

import java.io.*;
import java.util.Vector;

/** Класс служит для создания списка доступных таблиц со свойствами: <br>
 * {@link AvailableTables#fileName}, {@link AvailableTables#table},
 * {@link AvailableTables#row} <br>
 *  Методы класса: <br>
 * {@link AvailableTables#AvailableTables()}, {@link AvailableTables#addTable(String, String, String)},
 * {@link AvailableTables#saveTable()},
 * {@link AvailableTables#removeTable(String)},
 * {@link AvailableTables#getMainServerIP(String)}, {@link AvailableTables#getReserveServerIP(String)}<br>
 * @author Vladimirova Anna
 */

public class AvailableTables {
    /** Свойство - путь к файлу списка таблиц*/
    String fileName = "src\\mainserver\\AvailableTables";

    /** Свойство - список таблиц*/
    Vector<Vector<String>> table;

    /** Свойство - строка списка таблиц*/
    Vector<String> row;

    /** Создается новый объект {@link AvailableTables} <br>
     * Таблица считывается из файла и помещается в поле {@link AvailableTables#table}
     */
    AvailableTables(){
        table = new Vector<Vector<String>>();
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

    /** Добавление таблицы в список таблиц
     * @param tableName имя таблицы
     * @param mainServerIP IP адрес основного сервера
     * @param reserveServerIP IP адрес резервного сервера
     * @return -1, если такая таблица уже есть в списке,<br>
     * 0, в случае успешного добавления.
     */
    public int addTable(String tableName, String mainServerIP, String reserveServerIP){
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

    /** Удаление таблицы из списка таблиц
     * @param tableName имя таблицы
     * @return 1, в случае неудачи,<br>
     * 0, в случае успешного удаления.
     */
    public int removeTable(String tableName){
        for(int i = 0; i < table.size(); i++){
            if (table.get(i).get(0).equals(tableName)) {
                table.remove(i);
                return 0;
            }
        }
        return 1;
    }

    /** Запись таблица в файл
     * @return -1, если такая таблица уже есть в списке,<br>
     * 0, в случае успешной записи <br>
     * 1, в случае ошибки при открытии файла <br>
     * 2, в случае ошибки при записи в файл
     */
    public int saveTable(){
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
                return 2; //ошибка записи в файл
            }

        }catch (IOException e) {
            e.printStackTrace();
            return 1; //ошибка при открытии файла
        }
        return 0;
    }

    /** Возвращает IP главного сервера для таблицы
     * @param tableName имя таблицы
     * @return mainServerIP
     */
    public String getMainServerIP(String tableName){
        String mainServerIP = "";
        for(int i = 0; i < table.size(); i++){
            if (table.get(i).get(0).equals(tableName))
                mainServerIP = table.get(i).get(1);
        }
        return mainServerIP;
    }

    /** Возвращает IP резервного сервера для таблицы
     * @param tableName имя таблицы
     * @return reserveServerIP
     */
    public String getReserveServerIP(String tableName){
        String reserveServerIP = "";
        for(int i = 0; i < table.size(); i++){
            if (table.get(i).get(0).equals(tableName))
                reserveServerIP = table.get(i).get(2);
        }
        return reserveServerIP;
    }

}
