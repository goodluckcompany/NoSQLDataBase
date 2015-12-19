package simpleclient;

import mainserver.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/** Класс служит для создания соединения клиента с сервером
 *  Поля класса: <br>
 * {@link NoSqlConnector#socket}, {@link NoSqlConnector#response},{@link NoSqlConnector#request},
 * {@link NoSqlConnector#oos},
 * {@link NoSqlConnector#ois},
 * {@link NoSqlConnector#MAIN_PORT},
 * {@link NoSqlConnector#mainServerIP} <br>
 *  Методы класса: <br>
 * {@link NoSqlConnector#establishConnection()},
 * {@link NoSqlConnector#sendCommand(String)},
 * {@link NoSqlConnector#breakConnection()}<br>
 * @author Maslov Nikita
 */
public class NoSqlConnector {
    /** Сокет соединения с сервером*/
    Socket socket;
    /** Ответ сервера*/
    Request response;
    /** Запрос к серверу*/
    Request request;
    /** Поток вывода с сервера*/
    ObjectOutputStream oos;
    /** Поток передачи данных на сервер*/
    ObjectInputStream ois;
    /** Порт главного сервера*/
    int MAIN_PORT = 6661;
    /** IP адрес главного сервера*/
    String mainServerIP;

    /** Создается новый объект {@link NoSqlConnector} <br>
     * Инициализируется поле {@link NoSqlConnector#mainServerIP}
     */
    NoSqlConnector(String ip){
        this.mainServerIP = ip;
    }

    /** Устанавливается соединение с сервером <br>
     * @return 0, в случае успеха <br>
     * 1, в случае ошибки при создании сокета<br>
     * 2, в случае ошибки при создании потока передачи данных на сервер
     */
    int establishConnection(){
        try {
            this.socket = new Socket(mainServerIP, MAIN_PORT);
        } catch (IOException e) {
            System.err.println(e);
            return 1;
        }
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
        } catch (IOException e) {
            System.err.println(e);
            return 2;
        }
        return 0;
    }

    /** Отправляется команда клиента на сервер <br>
     * @param command отправляемая команда
     * @return response - ответ сервера
     */
    Request sendCommand(String command){
        request = new Request(socket.getInetAddress().toString(),command);
        if(socket.isConnected()){
            try{
                oos.flush();
                oos.writeObject(request);
                oos.flush();
            } catch (IOException e) {
                System.err.println(e);
            }
            try {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                System.err.println(e);
            }

            try{
                response = (Request)ois.readObject();
            } catch (ClassNotFoundException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        return response;
    }

    /** Закрывается соединение с сервером <br>
     * @return 0, в случае успеха <br>
     * 1, в случае ошибки при закрытии потока вывода<br>
     * 2, в случае ошибки при закрытии потока ввода,
     * 3, в случае ошибки при закрытии сокета
     */
    int breakConnection(){
        try {
            oos.close();
        } catch (IOException e) {
            System.err.println(e);
            return 1;
        }
        try {
            ois.close();
        } catch (IOException e) {
            System.err.println(e);
            return 2;
        }
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e);
            return 3;
        }
        return 0;
    }
}
