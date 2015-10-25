package com.database;

/**
 * Клиент базы данных
 */
import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] ar) {
        int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
        String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.
        // Здесь указан адрес того самого компьютера где будет исполняться и клиент.

        try {
            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Connecting to server...");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип
            // DataInputStream in = new DataInputStream(sin);
            ObjectInputStream ois = new ObjectInputStream(sin); /// создаем поток для считывания объектов
            // DataOutputStream out = new DataOutputStream(sout);

            TestSer ts = new TestSer();
            ts = (TestSer)ois.readObject(); // Десериализуем объект из входного потока

            System.out.println("Server object name - " + ts.name);
            System.out.println();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}