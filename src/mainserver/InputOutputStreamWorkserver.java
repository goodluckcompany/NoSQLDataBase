package mainserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/** Класс содержит потоки для общения с {@link workserver.Workserver} <br>
 *  Поля класса: <br>
 * {@link InputOutputStreamWorkserver#ipAdress},
 * {@link InputOutputStreamWorkserver#socket},
 * {@link InputOutputStreamWorkserver#oos},
 * {@link InputOutputStreamWorkserver#ios},
 * {@link InputOutputStreamWorkserver#status},
 * {@link InputOutputStreamWorkserver#WORK_PORT} <br>
 *  Методы класса: <br>
 * {@link InputOutputStreamWorkserver#InputOutputStreamWorkserver(Socket)},
 * {@link InputOutputStreamWorkserver#getIpAdress()},
 * {@link InputOutputStreamWorkserver#getSocket()},
 * {@link InputOutputStreamWorkserver#getStatus()} <br>
 * @author Maslov Nikita
 */
public class InputOutputStreamWorkserver {
    /** Поле - IP адрес рабочего сервера*/
    String ipAdress;

    /** Сокет рабочего сервера*/
    Socket socket;

    /** Поток вывода*/
    public ObjectOutputStream oos;

    /** Поток ввода*/
    public ObjectInputStream ios;

    /** Статус сервера: 0 - выключен, 1 - включен*/
    int status = 0;

    /** Порт рабочего сервера*/
    int WORK_PORT = 6667;

    /** Создается новый объект {@link InputOutputStreamWorkserver}
     * @param _socket сокет рабочего сервера
     */
    InputOutputStreamWorkserver(Socket _socket) {
        ipAdress = _socket.getInetAddress().toString();
        socket = _socket;

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Object output stream  for socket: " + ipAdress + " : " + WORK_PORT + " do not create!");
        }
        try {
            ios = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Object input stream  for socket: " + ipAdress + " : " + WORK_PORT + " do not create!");
        }
        status = 1;
    }

    /** Возвращает IP адрес рабочего сервера
     * @return ipAdress
     */
    public String getIpAdress() {
        return ipAdress = ipAdress.replace("/","");
    }

    /** Возвращает сокет рабочего сервера
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }

    /** Возвращает статус сервера
     * @return status
     */
    public int getStatus(){
        return status;
    }

}
