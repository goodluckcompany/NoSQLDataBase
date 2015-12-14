package mainserver;

import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by homie on 23.11.2015.
 */
public class InputOutputStreamWorkserver {
    String ipAdress;
    Socket socket;
    public ObjectOutputStream oos;
    public ObjectInputStream ios;
    int status = 0;/*Показывает статус сервера: 0 - выключен, 1 - включен*/

    int WORK_PORT = 6667;

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

    public String getIpAdress() {
        return ipAdress = ipAdress.replace("/","");
    }

    public Socket getSocket() {
        return socket;
    }

    public int getStatus(){
        return status;
    }

}
