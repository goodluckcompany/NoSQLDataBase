package simpleclient;

import mainserver.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** Класс служит для создания клиента <br>
  *  Поля класса: <br>
  * {@link SimpleClient#req}, {@link SimpleClient#br},{@link SimpleClient#command},
  * {@link SimpleClient#conn} <br>
  *  Методы класса: <br>
  * {@link SimpleClient#main(String[])}, {@link SimpleClient#SimpleClient()}<br>
  * @author Maslov Nikita
 */
public class SimpleClient {
    /** Результат запроса к БД*/
    Request req;
    /** Чтение с консоли*/
    BufferedReader br;
    /** Команда, вводимая клиентом*/
    String command;
    /** Соединение с сервером*/
    NoSqlConnector conn;

    /** Создается новый объект {@link SimpleClient}*/
    public static void main(String[] args){
        new SimpleClient();
    }

    /** Создается новый объект {@link SimpleClient} <br>
     * Устанавливается соединение, считывается команда с консоли,
     * отправляется на сервер и принимается ответ
     * @see NoSqlConnector
     * @see Request
     */
    SimpleClient(){
        br = new BufferedReader(new InputStreamReader(System.in));
        command = "";
        conn = new NoSqlConnector("172.18.27.29");
        if(conn.establishConnection() == 0){
            System.out.println("Input command:");
            try {
                command = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(!command.equalsIgnoreCase("stop")) {
                req = conn.sendCommand(command);
                System.out.println(req.toString());
                System.out.println("Input command:");
                try {
                    command = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.breakConnection();
        }
    }


}
